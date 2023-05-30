package com.tva.cashcoach.appcomponents.googleauth

import android.app.Activity
import android.content.Intent
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import com.tva.cashcoach.appcomponents.model.user.User
import com.tva.cashcoach.appcomponents.persistence.AppDatabase
import com.tva.cashcoach.appcomponents.persistence.repository.user.UserRepository
import com.tva.cashcoach.appcomponents.utility.PreferenceHelper
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 * Helper class for Google authentication.
 *
 * @param activity The [ComponentActivity] that will handle the authentication flow.
 * @param onSuccess Callback function that will be called when the authentication is successful.
 * @param onError Callback function that will be called when an error occurs during the authentication process.
 */
class GoogleAuthHelper(
    private val activity: ComponentActivity,
    private val onSuccess: (account: GoogleSignInAccount, reqSetup: Boolean) -> Unit,
    private val onError: (statusCode: Int) -> Unit,
    private val appDb: AppDatabase,
    private val preferenceHelper: PreferenceHelper,
    private val userRepository: UserRepository
) {

    private var mGoogleSignInClient: GoogleSignInClient? = null

    /**
     * Activity result launcher for Google sign-in.
     */
    private val startForGoogleResult =
        activity.registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                handleResult(result.data)
            }
        }

    /**
     * Starts the Google sign-in flow.
     */
    @OptIn(DelicateCoroutinesApi::class)
    fun login() {
        configureGoogleSignIn()
        val account = GoogleSignIn.getLastSignedInAccount(activity)
        if (account != null) {
            val firebaseUser = FirebaseAuth.getInstance().currentUser
            preferenceHelper.putString(
                "curr_user_uid",
                firebaseUser?.uid ?: ""
            )
            GlobalScope.launch(Dispatchers.IO) {
                val currUser = userRepository.get(firebaseUser?.uid ?: "")
                preferenceHelper.putString(
                    "curr_wallet_id",
                    currUser?.default_wallet_id.toString()
                )
                preferenceHelper.putString(
                    "curr_user_currency",
                    currUser?.currency.toString()
                )
            }.start()
            preferenceHelper.putBoolean(
                "googleSignedIn",
                true
            )
            onSuccess(account, false)
        } else {
            signIn()
        }
    }

    /**
     * Configures the Google sign-in options.
     */
    private fun configureGoogleSignIn() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("337861660196-gfr7d3as1el012stvglf1uglc9gaelmh.apps.googleusercontent.com")
            .requestProfile()
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(activity, gso)
    }

    /**
     * Handles the result of the Google sign-in flow.
     *
     * @param data The [Intent] containing the result data.
     */
    @OptIn(DelicateCoroutinesApi::class)
    fun handleResult(data: Intent?) {
        val task = GoogleSignIn.getSignedInAccountFromIntent(data)
        try {
            val account: GoogleSignInAccount? = task.getResult(ApiException::class.java)
            if (account != null) {
                val credential = GoogleAuthProvider.getCredential(account.idToken, null)
                FirebaseAuth.getInstance().signInWithCredential(credential)
                    .addOnCompleteListener {
                        if (task.isSuccessful) {
                            // User is signed in, check if the user exists in Firestore
                            val firebaseUser = FirebaseAuth.getInstance().currentUser
                            val userRef = FirebaseFirestore.getInstance().collection("users")
                                .document(firebaseUser?.uid ?: "")
                            userRef.get()
                                .addOnSuccessListener { document ->
                                    if (document.exists()) {
                                        // User already exists in Firestore, we assume that the user exists in the local database as well
                                        preferenceHelper.putString(
                                            "curr_user_uid",
                                            firebaseUser?.uid ?: ""
                                        )
                                        GlobalScope.launch(Dispatchers.IO) {
                                            val currUser =
                                                userRepository.get(firebaseUser?.uid ?: "")
                                            preferenceHelper.putString(
                                                "curr_wallet_id",
                                                currUser?.default_wallet_id.toString()
                                            )
                                            preferenceHelper.putString(
                                                "curr_user_currency",
                                                currUser?.currency.toString()
                                            )
                                        }.start()
                                        preferenceHelper.putBoolean(
                                            "googleSignedIn",
                                            true
                                        )
                                        onSuccess(account, false)
                                    } else {
                                        // User does not exist in Firestore, save the user
                                        val user = User(
                                            id = null,
                                            uid = firebaseUser?.uid ?: "",
                                            name = account.givenName ?: "",
                                            surname = account.familyName ?: "",
                                            email = account.email ?: "",
                                            currency = "EUR", // default value
                                            language = "en", // default value
                                            theme = "light", // default value
                                            avatar = account.photoUrl?.toString()
                                                ?: "https://t4.ftcdn.net/jpg/05/49/98/39/360_F_549983970_bRCkYfk0P6PP5fKbMhZMIb07mCJ6esXL.jpg",
                                            default_wallet_id = 0 // default value
                                        )
                                        userRef.set(user)
                                            .addOnSuccessListener {
                                                // Insert the user into the local database
                                                insertUserInLocalDb(user)
                                                preferenceHelper.putString(
                                                    "curr_user_uid",
                                                    firebaseUser?.uid ?: ""
                                                )
                                                GlobalScope.launch(Dispatchers.IO) {
                                                    val currUser =
                                                        userRepository.get(firebaseUser?.uid ?: "")
                                                    preferenceHelper.putString(
                                                        "curr_wallet_id",
                                                        currUser?.default_wallet_id.toString()
                                                    )
                                                    preferenceHelper.putString(
                                                        "curr_user_currency",
                                                        currUser?.currency.toString()
                                                    )
                                                }.start()
                                                preferenceHelper.putBoolean(
                                                    "googleSignedIn",
                                                    true
                                                )
                                                onSuccess(account, true)
                                            }
                                            .addOnFailureListener { e ->
                                                onError(e.message?.toInt() ?: 0)
                                            }
                                    }
                                }
                                .addOnFailureListener { e ->
                                    onError(e.message?.toInt() ?: 0)
                                }
                        } else {
                            onError(task.exception?.message?.toInt() ?: 0)
                        }
                    }
            }
        } catch (e: ApiException) {
            onError(e.statusCode)
        }
    }

    /**
     * Starts the Google sign-in flow.
     */
    private fun signIn() {
        val signInIntent: Intent = mGoogleSignInClient!!.signInIntent
        startForGoogleResult.launch(signInIntent)
    }

    /**
     * Inserts a user into the local database.
     *
     * @param user The user to insert.
     */
    @OptIn(DelicateCoroutinesApi::class)
    private fun insertUserInLocalDb(user: User) {
        userExistsInLocalDb(user.uid) { exists ->
            if (!exists) {
                GlobalScope.launch(Dispatchers.IO) {
                    appDb.getUserDao().insert(user)
                }
            }
        }
    }

    /**
     * Checks if a user exists in the local database.
     * @param id The user's ID.
     * @return True if the user exists, false otherwise.
     */
    @OptIn(DelicateCoroutinesApi::class)
    private fun userExistsInLocalDb(uid: String, callback: (Boolean) -> Unit) {
        GlobalScope.launch(Dispatchers.IO) {
            val user = appDb.getUserDao().getByUid(uid)
            callback(user != null)
        }
    }

    /**
     * Signs out the user from Google.
     *
     * @param onComplete Callback function that will be called when the sign-out process is complete.
     */
    fun signOut(onComplete: (task: Task<Void>) -> Unit) {
        configureGoogleSignIn()
        mGoogleSignInClient?.signOut()?.addOnCompleteListener(
            activity
        ) { task -> onComplete(task) }
    }
}