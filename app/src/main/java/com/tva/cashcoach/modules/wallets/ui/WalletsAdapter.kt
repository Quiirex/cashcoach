package com.tva.cashcoach.modules.wallets.ui

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tva.cashcoach.R
import com.tva.cashcoach.infrastructure.model.wallet.Wallet
import com.tva.cashcoach.infrastructure.persistence.repository.wallet.WalletRepository
import com.tva.cashcoach.infrastructure.utility.PreferenceHelper
import com.tva.cashcoach.databinding.RowWalletBinding
import com.tva.cashcoach.modules.wallets.data.model.WalletsRowModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class WalletsAdapter(
    private val walletRepository: WalletRepository,
    val preferenceHelper: PreferenceHelper
) : RecyclerView.Adapter<WalletsAdapter.RowAccountsVH>() {
    private var clickListener: OnItemClickListener? = null
    private var wallets: List<Wallet> = emptyList()
    private var selectedPosition: Int = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RowAccountsVH {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_wallet, parent, false)
        return RowAccountsVH(view)
    }

    override fun onBindViewHolder(
        holder: RowAccountsVH,
        @SuppressLint("RecyclerView") position: Int
    ) {
        val wallet = wallets[position]
        val walletsRowModel = WalletsRowModel(wallet.name, wallet.balance)
        holder.binding.accountsRowModel = walletsRowModel
        holder.binding.txtWalletName.text = wallet.name

        val currency = when (preferenceHelper.getString("curr_user_currency", "EUR")) {
            "EUR" -> "€"
            "USD" -> "$"
            else -> "€"
        }

        holder.binding.txtWalletBalance.text = wallet.balance.toString() + currency

        // Get the default_wallet_id from the preferenceHelper
        val defaultWalletId = preferenceHelper.getString("curr_wallet_id", "").toInt()

        // Check if the current wallet id matches the default_wallet_id and set the radio button accordingly
        holder.binding.radioDefaultWallet.isChecked = wallet.id == defaultWalletId

        // Update the selectedPosition
        if (wallet.id == defaultWalletId) {
            selectedPosition = position
        }

        // Set a click listener for the radio button
        holder.binding.radioDefaultWallet.setOnClickListener {
            if (selectedPosition != position) {
                // Update the default_wallet_id in the preferenceHelper
                preferenceHelper.putString("curr_wallet_id", wallet.id.toString())
                Log.d("Change default wallet", "Wallet id: " + wallet.id.toString())
                // Update the selectedPosition and refresh the adapter
                selectedPosition = position
                notifyDataSetChanged()
            }
        }
    }

    override fun getItemCount(): Int = wallets.size

    fun setOnItemClickListener(clickListener: OnItemClickListener) {
        this.clickListener = clickListener
    }

    interface OnItemClickListener {
        fun onItemClick(
            view: View,
            position: Int,
            item: WalletsRowModel
        ) {
        }
    }

    inner class RowAccountsVH(
        view: View
    ) : RecyclerView.ViewHolder(view) {
        val binding: RowWalletBinding = RowWalletBinding.bind(itemView)
    }

    suspend fun fetchWallets() {
        val currentUserId = preferenceHelper.getString("curr_user_uid", "")
        wallets = withContext(Dispatchers.IO) {
            walletRepository.getAllByUid(currentUserId)
        }
        notifyDataSetChanged()
    }
}
