package com.tva.cashcoach.appcomponents.utility

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.tva.cashcoach.appcomponents.di.MyApp
import com.tva.cashcoach.appcomponents.model.transaction.Transaction
import com.tva.cashcoach.appcomponents.model.wallet.Wallet
import com.tva.cashcoach.appcomponents.persistence.repository.transaction.TransactionRepository
import com.tva.cashcoach.appcomponents.persistence.repository.user.UserRepository
import com.tva.cashcoach.appcomponents.persistence.repository.wallet.WalletRepository
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.android.ext.android.get
import java.util.Date

class WalletHelper(
    private val walletRepository: WalletRepository,
    private val userRepository: UserRepository,
    private val transactionRepository: TransactionRepository
) {
    var preferenceHelper: PreferenceHelper = MyApp.getInstance().get()

    /**
     * Adds a new wallet to the database.
     *
     * @param name The name of the wallet.
     * @param type The type of the wallet.
     * @param budget The budget of the wallet.
     */
    @OptIn(DelicateCoroutinesApi::class)
    fun addWallet(name: String, type: String, budget: Double, callback: (Int) -> Unit) {
        try {
            val userId = preferenceHelper.getString("curr_user_uid", "")
            Log.w("WalletHelper", "current user id: $userId")
            if (userId == "") {
                callback(-1)
                return
            }
            val wallet = Wallet(null, name, type, budget, userId)
            syncWalletToFirestore(wallet, callback = { success ->
                if (success) {
                    Log.d("WalletHelper", "synced wallet to firestore")
                } else {
                    Log.w("WalletHelper", "failed to sync wallet to firestore")
                }
            })
            GlobalScope.launch(Dispatchers.IO) {
                val insertedWalletId = walletRepository.insert(wallet).toInt()

                // Create a new transaction for the added wallet
                val newTransaction = Transaction(
                    id = null,
                    value = budget,
                    description = "Wallet budget",
                    date = Date(),
                    category = "Initial",
                    wallet_id = insertedWalletId,
                    type = "income",
                    currency = "EUR"
                )

                // Insert the new transaction into the transaction repository
                withContext(Dispatchers.IO) {
                    transactionRepository.insert(newTransaction)
                }

                withContext(Dispatchers.Main) {
                    callback(insertedWalletId)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            callback(-1)
        }
    }


    /**
     * Sets default wallet of the current user
     * @param walletId The id of the wallet.
     * @return true if successful, false otherwise.
     */
    @OptIn(DelicateCoroutinesApi::class)
    fun setDefaultWallet(walletId: Int): Boolean {
        try {
            val userId = preferenceHelper.getString("curr_user_uid", "")
            if (userId == "") return false
            GlobalScope.launch(Dispatchers.IO) {
                userRepository.setDefaultWalletId(userId, walletId)
            }
            return true
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
    }

    /**
     * Sync wallets of the current user with firestore
     * @return true if successful, false otherwise.
     */
    fun syncWalletToFirestore(wallet: Wallet, callback: (Boolean) -> Unit) {
        val firebaseUser = FirebaseAuth.getInstance().currentUser
        val userRef = FirebaseFirestore.getInstance().collection("users")
            .document(firebaseUser?.uid ?: "")
        userRef.get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    // create an empty field "wallets" array for the user if it doesn't exist already and add the wallet, or else just add the wallet to the existing array
                    val wallets = document.data?.get("wallets") as? ArrayList<HashMap<String, Any>>
                    if (wallets == null) {
                        val newWallets = arrayListOf<HashMap<String, Any>>()
                        val newWallet = hashMapOf<String, Any>(
                            "name" to wallet.name,
                            "type" to wallet.type,
                            "balance" to wallet.balance,
                        )
                        newWallets.add(newWallet)
                        userRef.update("wallets", newWallets)
                        callback(true)
                    } else {
                        val newWallet = hashMapOf<String, Any>(
                            "name" to wallet.name,
                            "type" to wallet.type,
                            "balance" to wallet.balance,
                        )
                        wallets.add(newWallet)
                        userRef.update("wallets", wallets)
                        callback(true)
                    }
                } else {
                    Log.d("WalletHelper", "No such document")
                    callback(false)
                }
            }
            .addOnFailureListener { exception ->
                Log.w("WalletHelper", "Error getting documents: ", exception)
                callback(false)
            }
    }

    /**
     * Displays the total balance of all wallets of the current user.
     */
    @OptIn(DelicateCoroutinesApi::class)
    fun getTotalBalanceOfWallets(callback: (Double) -> Unit) {
        val userId = preferenceHelper.getString("curr_user_uid", "")
        if (userId == "") {
            callback(0.0)
            return
        }
        var totalBalance = 0.0
        GlobalScope.launch(Dispatchers.IO) {
            val wallets = walletRepository.getAllByUid(userId)
            for (wallet in wallets) {
                totalBalance += wallet.balance
            }
            withContext(Dispatchers.Main) {
                callback(totalBalance)
            }
        }
    }
}
