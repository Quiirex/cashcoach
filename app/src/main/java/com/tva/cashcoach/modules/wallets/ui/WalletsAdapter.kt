package com.tva.cashcoach.modules.wallets.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tva.cashcoach.R
import com.tva.cashcoach.appcomponents.model.wallet.Wallet
import com.tva.cashcoach.appcomponents.persistence.repository.wallet.WalletRepository
import com.tva.cashcoach.appcomponents.utility.PreferenceHelper
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RowAccountsVH {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_wallet, parent, false)
        return RowAccountsVH(view)
    }

    override fun onBindViewHolder(holder: RowAccountsVH, position: Int) {
        val wallet = wallets[position]
        val walletsRowModel = WalletsRowModel(wallet.name, wallet.balance)
        holder.binding.accountsRowModel = walletsRowModel
        holder.binding.txtWalletName.text = wallet.name
        holder.binding.txtWalletBalance.text =
            wallet.balance.toString() + "€" // TODO: Change to currency
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

