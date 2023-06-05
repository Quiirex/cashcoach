package com.tva.cashcoach.modules.transaction.ui

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.DatePicker
import android.widget.EditText
import android.widget.GridLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.tva.cashcoach.R
import com.tva.cashcoach.infrastructure.base.BaseFragment
import com.tva.cashcoach.infrastructure.model.transaction.TransactionDao
import com.tva.cashcoach.infrastructure.persistence.repository.transaction.TransactionRepository
import com.tva.cashcoach.databinding.FragmentTransactionBinding
import com.tva.cashcoach.modules.expensedetail.ui.ExpenseDetailActivity
import com.tva.cashcoach.modules.homescreencontainer.ui.HomeScreenContainerActivity
import com.tva.cashcoach.modules.incomedetail.ui.IncomeDetailActivity
import com.tva.cashcoach.modules.transaction.data.model.TransactionRowModel
import com.tva.cashcoach.modules.transaction.data.viewmodel.TransactionVM
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class TransactionFragment :
    BaseFragment<FragmentTransactionBinding>(R.layout.fragment_transaction) {
    private val viewModel: TransactionVM by viewModels()

    private lateinit var transactionAdapter: TransactionAdapter

    private lateinit var transactionRepository: TransactionRepository

    private lateinit var transactionDao: TransactionDao

    private var startDate: Date? = null
    private var endDate: Date? = null
    private var selectedCategories: Array<String> = emptyArray()
    private var sortBy: String? = null
    private var transactionType: String? = null
    var tempDate: Date? = null

    override fun onInitialized() {
        viewModel.navArguments = arguments

        transactionDao = appDb.getTransactionDao()
        transactionRepository = TransactionRepository(transactionDao)

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

        binding.transactionVM = viewModel

        lifecycleScope.launch {
            transactionAdapter.fetchTransactions(preferenceHelper.getString("curr_wallet_id", ""))
        }
    }

    override fun setUpClicks() {
        binding.btnSort.setOnClickListener {
            val dialog = Dialog(requireContext())
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.setContentView(R.layout.transactions_filter)
            setUpDatePickers(dialog)
            setDate(dialog)

            val applyFilters = dialog.findViewById<Button>(R.id.buttonApply)
            val resetFilters = dialog.findViewById<Button>(R.id.buttonReset)


            applyFilters.setOnClickListener {
                filter(dialog)
                dialog.dismiss()
            }

            resetFilters.setOnClickListener {
                setDate(dialog)
                setDefaults(dialog)
            }

            dialog.show()
        }
    }

    private fun filter(dialog: Dialog) {
        val startDateEditText = dialog.findViewById<EditText>(R.id.startDate)
        val endDateEditText = dialog.findViewById<EditText>(R.id.endDate)
        val dateFormatter = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
        val startDateString = startDateEditText.text.toString()
        val endDateString = endDateEditText.text.toString()

        val radioGroupTransaction =
            dialog.findViewById<RadioGroup>(R.id.radioGroupFilterTransaction)
        val checkedRadioButtonId = radioGroupTransaction.checkedRadioButtonId
        val selectedTransaction = dialog.findViewById<RadioButton>(checkedRadioButtonId)

        val radioGroupSort = dialog.findViewById<RadioGroup>(R.id.radioGroupFilterSortBy)
        val checkedSortButtonId = radioGroupSort.checkedRadioButtonId
        val selectedSortBy = dialog.findViewById<RadioButton>(checkedSortButtonId)

        val categories = dialog.findViewById<GridLayout>(R.id.CategoryFilter)

        startDate = parseDate(startDateEditText.text.toString())
        tempDate = parseDate(endDateEditText.text.toString())
        tempDate?.let { tempDate ->
            val calendar = Calendar.getInstance()
            calendar.time = tempDate
            calendar.add(Calendar.DAY_OF_MONTH, 1)
            endDate = calendar.time
        }

        transactionType = selectedTransaction.text.toString()
        sortBy = selectedSortBy.text.toString()
        selectedCategories = Array<String>(categories.childCount) { "" }
        for (i in 0 until categories.childCount) {
            val view = categories.getChildAt(i)
            if (view is CheckBox) {
                if (view.isChecked) {
                    selectedCategories[i] = view.text.toString()
                }
            }
        }

        // Pass the filter parameters to the adapter to update the dataset
        transactionAdapter.filterTransactions(
            startDate,
            endDate,
            transactionType,
            sortBy,
            selectedCategories
        )
        dialog.dismiss()

    }

    private fun parseDate(dateString: String): Date? {
        return SimpleDateFormat("dd.MM.yyyy", Locale.US).parse(dateString)
    }


    private fun setUpDatePickers(dialog: Dialog) {
        val startDateEditText: EditText = dialog.findViewById(R.id.startDate)
        val endDateEditText: EditText = dialog.findViewById(R.id.endDate)

        startDateEditText.apply {
            isFocusable = false
            isFocusableInTouchMode = false
            setOnClickListener {
                showDatePicker(startDateEditText)
            }
        }

        endDateEditText.apply {
            isFocusable = false
            isFocusableInTouchMode = false
            setOnClickListener {
                showDatePicker(endDateEditText)
            }
        }
    }

    private fun showDatePicker(editText: EditText) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            requireContext(),
            R.style.DatePickerTheme,
            { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
                val selectedDate = Calendar.getInstance()
                selectedDate.set(year, month, dayOfMonth)

                val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.US)
                val dateString = dateFormat.format(selectedDate.time)
                editText.setText(dateString)
            },
            year,
            month,
            day
        )

        datePickerDialog.show()
    }

    fun setDate(dialog: Dialog) {
        val startDate = dialog.findViewById<EditText>(R.id.startDate)
        val endDate = dialog.findViewById<EditText>(R.id.endDate)
        val sdf = SimpleDateFormat("dd.MM.yyyy", Locale.US)

        val defaultStartDate = sdf.parse("01.01.2023")
        startDate.setText(sdf.format(defaultStartDate))

        val currentDate = Date()
        endDate.setText(sdf.format(currentDate))
    }

    fun setDefaults(dialog: Dialog) {
        val radioGroupTransaction =
            dialog.findViewById<RadioGroup>(R.id.radioGroupFilterTransaction)
        radioGroupTransaction.clearCheck()

        val radioGroupSortBy = dialog.findViewById<RadioGroup>(R.id.radioGroupFilterSortBy)
        radioGroupSortBy.clearCheck()

        val defaults = arrayOf(
            dialog.findViewById<RadioButton>(R.id.radioButtonTransactionAll),
            dialog.findViewById<RadioButton>(R.id.radioButtonSortNewest),
            dialog.findViewById<CheckBox>(R.id.radioFoodDrinks),
            dialog.findViewById<CheckBox>(R.id.radioClothesShoes),
            dialog.findViewById<CheckBox>(R.id.radioTransportation),
            dialog.findViewById<CheckBox>(R.id.radioHealthBeauty),
            dialog.findViewById<CheckBox>(R.id.radioHomeUtilities),
            dialog.findViewById<CheckBox>(R.id.radioEntertainment),
            dialog.findViewById<CheckBox>(R.id.radioGiftsDonations),
            dialog.findViewById<CheckBox>(R.id.radioEducation),
            dialog.findViewById<CheckBox>(R.id.radioOther)
        )
        for (button in defaults) {
            button.isChecked = true
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
        const val TAG: String = "TRANSACTION_FRAGMENT"

        var currentFragmentTag: String? = null

        fun getInstance(bundle: Bundle?): TransactionFragment {
            val fragment = TransactionFragment()
            fragment.arguments = bundle
            return fragment
        }
    }
}
