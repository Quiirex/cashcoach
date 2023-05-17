package com.tva.cashcoach.appcomponents.auth

import androidx.activity.ComponentActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.tva.cashcoach.appcomponents.model.user.User

/**
 * Helper class for Firebase email/password authentication.
 *
 * @param activity The [ComponentActivity] that will handle the authentication flow.
 * @param onSuccess Callback function that will be called when the authentication is successful.
 * @param onError Callback function that will be called when an error occurs during the authentication process.
 */
class AuthHelper(
    private val activity: ComponentActivity,
    private val onSuccess: (user: FirebaseUser) -> Unit,
    private val onError: (errorCode: String) -> Unit
) {

    /**
     * Starts the email/password sign-in flow.
     *
     * @param email The user's email.
     * @param password The user's password.
     */
    fun login(email: String, password: String) {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(activity) { task ->
                if (task.isSuccessful) {
                    val user = FirebaseAuth.getInstance().currentUser
                    if (user != null) {
                        onSuccess(user)
                    } else {
                        onError("ERROR_USER_IS_NULL")
                    }
                } else {
                    onError("ERROR_INVALID_CREDENTIALS")
                }
            }
    }

    /**
     * Starts the email/password sign-up flow.
     *
     * @param email The user's email.
     * @param password The user's password.
     * @param name The user's name.
     * @param surname The user's surname.
     */
    fun signUp(email: String, password: String, name: String, surname: String) {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(activity) { task ->
                if (task.isSuccessful) {
                    val user = FirebaseAuth.getInstance().currentUser
                    if (user != null) {
                        createFirestoreUser(user.uid, email, name, surname)
                    } else {
                        onError("ERROR_USER_IS_NULL")
                    }
                } else {
                    val exception = task.exception
                    if (exception is FirebaseAuthUserCollisionException) {
                        // Email is already in use, handle accordingly
                        onError("ERROR_EMAIL_ALREADY_IN_USE")
                    } else {
                        val errorCode = (exception as? FirebaseAuthInvalidUserException)?.errorCode
                            ?: (exception as? FirebaseAuthInvalidCredentialsException)?.errorCode
                            ?: "Unknown error"
                        onError(errorCode)
                    }
                }
            }
    }


    /**
     * Creates a user document in the "users" collection in Firestore.
     *
     * @param uid The user's UID.
     * @param email The user's email.
     * @param name The user's name.
     * @param surname The user's surname.
     */
    private fun createFirestoreUser(uid: String, email: String, name: String, surname: String) {
        val userRef = FirebaseFirestore.getInstance().collection("users")
            .document(uid)
        userRef.get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    // User already exists in Firestore, call onSuccess
                    onSuccess(FirebaseAuth.getInstance().currentUser!!)
                } else {
                    // User does not exist in Firestore, save the user
                    val user = User(
                        id = null,
                        uid = uid,
                        name = name,
                        surname = surname,
                        email = email,
                        currency = "EUR", // default value
                        language = "en", // default value
                        theme = "light", // default value
                        avatar = "https://t4.ftcdn.net/jpg/05/49/98/39/360_F_549983970_bRCkYfk0P6PP5fKbMhZMIb07mCJ6esXL.jpg" // default value
                    )
                    userRef.set(user)
                        .addOnSuccessListener {
                            onSuccess(FirebaseAuth.getInstance().currentUser!!)
                        }
                        .addOnFailureListener { e ->
                            onError(e.message ?: "Unknown error")
                        }
                }
            }
            .addOnFailureListener { e ->
                onError(e.message ?: "Unknown error")
            }
    }

    /**
     * Signs out the current user.
     */
    fun signOut() {
        FirebaseAuth.getInstance().signOut()
    }

    /**
     * Sends a reset password email to the user.
     *
     * @param email The user's email.
     * @param onSuccess Callback function that will be called when the reset password email is sent successfully.
     */
    fun forgotPassword(email: String, onSuccess: () -> Unit, onError: (errorCode: String) -> Unit) {
        val auth = FirebaseAuth.getInstance()
        auth.sendPasswordResetEmail(email)
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener { exception ->
                onError(exception.message ?: "Unknown error")
            }
    }
}