package com.tva.cashcoach.modules.homescreen.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.tva.cashcoach.R
import com.tva.cashcoach.databinding.FragmentHomeScreenBinding
import com.tva.cashcoach.infrastructure.base.BaseFragment
import com.tva.cashcoach.infrastructure.model.transaction.TransactionDao
import com.tva.cashcoach.infrastructure.model.user.UserDao
import com.tva.cashcoach.infrastructure.persistence.repository.transaction.TransactionRepository
import com.tva.cashcoach.infrastructure.persistence.repository.user.UserRepository
import com.tva.cashcoach.infrastructure.ui.loadImageFromURL
import com.tva.cashcoach.modules.expensedetail.ui.ExpenseDetailActivity
import com.tva.cashcoach.modules.homescreen.data.viewmodel.HomeScreenVM
import com.tva.cashcoach.modules.homescreencontainer.ui.HomeScreenContainerActivity
import com.tva.cashcoach.modules.incomedetail.ui.IncomeDetailActivity
import com.tva.cashcoach.modules.newexpense.ui.NewExpenseActivity
import com.tva.cashcoach.modules.newincome.ui.NewIncomeActivity
import com.tva.cashcoach.modules.profile.ui.ProfileFragment
import com.tva.cashcoach.modules.transaction.data.model.TransactionRowModel
import com.tva.cashcoach.modules.transaction.ui.TransactionAdapter
import com.tva.cashcoach.modules.transaction.ui.TransactionFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeScreenFragment : BaseFragment<FragmentHomeScreenBinding>(R.layout.fragment_home_screen) {
    private val viewModel: HomeScreenVM by viewModels()

    private lateinit var transactionAdapter: TransactionAdapter

    private lateinit var transactionRepository: TransactionRepository

    private lateinit var transactionDao: TransactionDao

    private lateinit var userDao: UserDao

    private lateinit var userRepository: UserRepository

    private var curr_wallet_id = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.navArguments = arguments

        transactionDao = appDb.getTransactionDao()
        transactionRepository = TransactionRepository(transactionDao)

        userDao = appDb.getUserDao()
        userRepository = UserRepository(userDao)

        transactionAdapter = TransactionAdapter(
            transactionRepository,
            preferenceHelper
        )

        binding.recyclerTransactions.adapter = transactionAdapter

        transactionAdapter.setOnItemClickListener(
            object : TransactionAdapter.OnItemClickListener {
                override fun onItemClick(
                    view: View,
                    position: Int,
                    transaction: TransactionRowModel
                ) {
                    onClickRecyclerTransactions(view, position, transaction)
                }
            }
        )

        binding.linearRowIncome.setOnClickListener {
            val intent = Intent(context, NewIncomeActivity::class.java)
            startActivity(intent)
        }

        binding.linearRowExpense.setOnClickListener {
            val intent = Intent(context, NewExpenseActivity::class.java)
            startActivity(intent)
        }

        binding.btnSeeAll.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.fragmentContainer, TransactionFragment())?.commit()
        }


        binding.homeScreenVM = viewModel

        curr_wallet_id = preferenceHelper.getString("curr_wallet_id", "")

        loadTransactionData()
    }

    private fun loadTransactionData() {
        val currentUserId = preferenceHelper.getString("curr_user_uid", "")
        lifecycleScope.launch {
            transactionAdapter.fetchTransactions(curr_wallet_id)
            val currencySymbol =
                if (preferenceHelper.getString("curr_user_currency", "") == "EUR") "€" else "$"
            val incomesSum = transactionAdapter.fetchIncomesSum(curr_wallet_id)
            val expensesSum = transactionAdapter.fetchExpensesSum(curr_wallet_id)
            val budget = incomesSum - expensesSum
            binding.valIncome.text = "${incomesSum.format()}$currencySymbol"
            binding.valExpenses.text = "${expensesSum.format()}$currencySymbol"
            binding.valBudget.text = "${budget.format()}$currencySymbol"

            val currentUser = withContext(Dispatchers.IO) {
                userRepository.get(currentUserId)
            }

            loadImageFromURL(
                binding.imageAvatar,
                currentUser?.avatar
                    ?: "https://t4.ftcdn.net/jpg/05/49/98/39/360_F_549983970_bRCkYfk0P6PP5fKbMhZMIb07mCJ6esXL.jpg",
                null,
                null,
                0.0f,
                true
            )
        }
    }

    private fun Double.format(): String = String.format("%.2f", this)

    override fun setUpClicks() {
        binding.imageAvatar.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.fragmentContainer, ProfileFragment())?.commit()
        }
    }

    fun onClickRecyclerTransactions(
        view: View,
        position: Int,
        transaction: TransactionRowModel
    ) {
        val intent = when (transaction.type) {
            "expense" -> Intent(context, ExpenseDetailActivity::class.java)
            "income" -> Intent(context, IncomeDetailActivity::class.java)
            else -> null
        }

        intent?.let {
            val bundle = Bundle().apply {
                putString("value", transaction.value.toString())
                putString("date", transaction.date)
                putString("category", transaction.category)
                putString("wallet_id", transaction.wallet_id.toString())
                putString("description", transaction.description)
                putString("currency", transaction.currency)
                putInt("id", transaction.id)
            }
            it.putExtra("bundle", bundle)
            startActivity(it)
        }
    }

    private fun updateButtonColors() {
        (activity as? HomeScreenContainerActivity)?.updateButtonColors(TAG)
    }

    override fun onResume() {
        super.onResume()
        loadTransactionData()
        currentFragmentTag = TAG
        updateButtonColors()
        lifecycleScope.launch {
            transactionAdapter.fetchTransactions(preferenceHelper.getString("curr_wallet_id", ""))
        }
    }

    override fun onPause() {
        super.onPause()
        currentFragmentTag = null
    }

    companion object {
        const val TAG: String = "HOME_SCREEN_FRAGMENT"

        var currentFragmentTag: String? = null

        fun getInstance(bundle: Bundle?): HomeScreenFragment {
            val fragment = HomeScreenFragment()
            fragment.arguments = bundle
            return fragment
        }
    }
}
