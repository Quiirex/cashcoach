package com.tva.cashcoach.infrastructure.utility

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.tva.cashcoach.R
import com.tva.cashcoach.infrastructure.application.App
import com.tva.cashcoach.infrastructure.model.transaction.Transaction
import com.tva.cashcoach.infrastructure.model.wallet.Wallet
import com.tva.cashcoach.infrastructure.persistence.repository.transaction.TransactionRepository
import com.tva.cashcoach.infrastructure.persistence.repository.user.UserRepository
import com.tva.cashcoach.infrastructure.persistence.repository.wallet.WalletRepository
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
    var preferenceHelper: PreferenceHelper = App.getInstance().get()

    /**
     * Adds a new wallet to the database.
     *
     * @param name The name of the wallet.
     * @param type The type of the wallet.
     * @param balance The budget of the wallet.
     */
    @OptIn(DelicateCoroutinesApi::class)
    fun addWallet(name: String, type: String, balance: Double, callback: (Int) -> Unit) {
        try {
            val userId = preferenceHelper.getString("curr_user_uid", "")
            Log.w("WalletHelper", "current user id: $userId")
            if (userId == "") {
                callback(-1)
                return
            }
            val tempWallet = Wallet(null, name, type, balance, userId)
            GlobalScope.launch(Dispatchers.IO) {
                val insertedWalletId = walletRepository.insert(tempWallet).toInt()

                val wallet = Wallet(
                    id = insertedWalletId,
                    name = name,
                    type = type,
                    balance = balance,
                    user_id = userId
                )

                addWalletToFirestore(wallet, callback = { success ->
                    if (success) {
                        Log.d("WalletHelper", "synced wallet to firestore")
                    } else {
                        Log.w("WalletHelper", "failed to sync wallet to firestore")
                    }
                })

                // Create a new transaction for the added wallet
                val newTransaction = Transaction(
                    id = null,
                    value = balance,
                    description = App.getInstance().resources.getString(R.string.wallet_budget),
                    date = Date(),
                    category = App.getInstance().resources.getString(R.string.initial_budget),
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
    fun addWalletToFirestore(wallet: Wallet, callback: (Boolean) -> Unit) {
        val firebaseUser = FirebaseAuth.getInstance().currentUser
        val userRef = FirebaseFirestore.getInstance().collection("users")
            .document(firebaseUser?.uid ?: "")
        userRef.get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    val wallets = document.data?.get("wallets") as? ArrayList<HashMap<String, Any>>
                    if (wallets == null) {
                        val newWallets = arrayListOf<HashMap<String, Any>>()
                        val newWallet = hashMapOf<String, Any>(
                            "name" to wallet.name,
                            "type" to wallet.type,
                            "balance" to wallet.balance,
                            "wallet_id" to wallet.id.toString()
                        )
                        newWallets.add(newWallet)
                        userRef.update("wallets", newWallets)
                        callback(true)
                    } else {
                        val newWallet = hashMapOf<String, Any>(
                            "name" to wallet.name,
                            "type" to wallet.type,
                            "balance" to wallet.balance,
                            "wallet_id" to wallet.id.toString()
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
     * Add transaction (income or expense) of the current user with firestore, by adding a new income to the incomes array or a new expense to the expenses array. And updates the balance of the wallet.
     * @return true if successful, false otherwise.
     * @param transaction The transaction.
     */
    fun addTransactionToFirestore(transaction: Transaction, callback: (Boolean) -> Unit) {
        val firebaseUser = FirebaseAuth.getInstance().currentUser
        val userRef = FirebaseFirestore.getInstance().collection("users")
            .document(firebaseUser?.uid ?: "")
        userRef.get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    val wallets = document.data?.get("wallets") as? ArrayList<HashMap<String, Any>>
                    if (wallets == null) {
                        callback(false)
                    } else {
                        for (wallet in wallets) {
                            if (wallet["wallet_id"] == transaction.wallet_id.toString()) {
                                val transactions =
                                    wallet[transaction.type] as? ArrayList<HashMap<String, Any>>
                                if (transactions == null) {
                                    val newTransactions = arrayListOf<HashMap<String, Any>>()
                                    val newTransaction = hashMapOf<String, Any>(
                                        "value" to transaction.value,
                                        "description" to transaction.description,
                                        "date" to transaction.date,
                                        "category" to transaction.category,
                                        "currency" to transaction.currency,
                                        "transaction_id" to transaction.id.toString()
                                    )
                                    newTransactions.add(newTransaction)
                                    wallet[transaction.type] = newTransactions
                                    userRef.update("wallets", wallets)
                                    callback(true)
                                } else {
                                    val newTransaction = hashMapOf<String, Any>(
                                        "value" to transaction.value,
                                        "description" to transaction.description,
                                        "date" to transaction.date,
                                        "category" to transaction.category,
                                        "currency" to transaction.currency,
                                        "transaction_id" to transaction.id.toString()
                                    )
                                    transactions.add(newTransaction)
                                    wallet[transaction.type] = transactions
                                    userRef.update("wallets", wallets)
                                    callback(true)
                                }
                            }
                        }
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
     * Removes transaction (income or expense) of the current user from firestore, by removing the income from the incomes array or the expense from the expenses array. And updates the balance of the wallet.
     * @return true if successful, false otherwise.
     * @param transaction The transaction.
     * @param walletId The id of the wallet.
     */
    fun removeTransactionFromFirestore(
        transaction: Transaction,
        walletId: Int,
        callback: (Boolean) -> Unit
    ) {
        val firebaseUser = FirebaseAuth.getInstance().currentUser
        val userRef = FirebaseFirestore.getInstance().collection("users")
            .document(firebaseUser?.uid ?: "")
        userRef.get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    val wallets = document.data?.get("wallets") as? ArrayList<HashMap<String, Any>>
                    if (wallets == null) {
                        callback(false)
                    } else {
                        for (wallet in wallets) {
                            if (wallet["wallet_id"] == walletId.toString()) {
                                val transactions =
                                    wallet[transaction.type] as? ArrayList<HashMap<String, Any>>
                                if (transactions == null) {
                                    callback(false)
                                } else {
                                    for (transactionMap in transactions) {
                                        if (transactionMap["transaction_id"] == transaction.id.toString()) {
                                            transactions.remove(transactionMap)
                                            wallet[transaction.type] = transactions
                                            userRef.update("wallets", wallets)
                                            callback(true)
                                        }
                                    }
                                }
                            }
                        }
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
        val currency = preferenceHelper.getString("curr_user_currency", "")
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

    /**
     * Returns wallet name by id
     */
    @OptIn(DelicateCoroutinesApi::class)
    fun getWalletNameById(walletId: Int, callback: (String) -> Unit) {
        GlobalScope.launch(Dispatchers.IO) {
            val wallet = walletRepository.getById(walletId)
            withContext(Dispatchers.Main) {
                callback(wallet.name)
            }
        }
    }
}
