package com.example.productapp.presentation.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.productapp.R
import com.example.productapp.databinding.FragmentProductItemBinding
import com.example.productapp.databinding.ProductItemBinding
import com.example.productapp.domain.ProductItem
import com.example.productapp.presentation.MainActivity.Companion.EXTRA_PRODUCT_ITEM_ID
import com.example.productapp.presentation.MainActivity.Companion.EXTRA_SCREEN_MODE
import com.example.productapp.presentation.MainActivity.Companion.MODE_ADD
import com.example.productapp.presentation.MainActivity.Companion.MODE_EDIT
import com.example.productapp.presentation.ProductItemViewModel
import com.google.android.material.textfield.TextInputLayout

class ProductItemFragment(
//    private val screenMode : String = MODE_UNKNOWN,
//    private val productItemId : Int = ProductItem.UNDEFINED_ID
) : Fragment() {

    private lateinit var viewModel: ProductItemViewModel
    private lateinit var binding: FragmentProductItemBinding

    private lateinit var tilName: TextInputLayout
    private lateinit var tilCount: TextInputLayout
    private lateinit var etName: EditText
    private lateinit var etCount: EditText
    private lateinit var saveButton: Button

//    fun newInstanceAddItem() : ProductItemFragment {
//        return ProductItemFragment(MODE_ADD)
//    }
//
//    fun newInstanceEditItem(productItemId: Int) : ProductItemFragment {
//        return ProductItemFragment(MODE_EDIT, productItemId)
//    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_product_item, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentProductItemBinding.inflate(layoutInflater)
        val view = binding.root
        val screenMode = arguments?.get(EXTRA_SCREEN_MODE).toString()
        val productItemId = arguments?.getInt(EXTRA_PRODUCT_ITEM_ID)
        parseParams(screenMode, productItemId)
        initViews(view)
        viewModel = ViewModelProvider(this).get(ProductItemViewModel::class.java)
        addTextChangeListeners()
        when (screenMode) {
            MODE_EDIT -> startEditMode(productItemId)
            MODE_ADD -> startAddMode()
        }
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.errorInputCount.observe(this) {
            val message = if (it) {
                getString(R.string.error_input_count)
            } else {
                null
            }
            tilCount.error = message
        }
        viewModel.errorInputName.observe(this) {
            val message = if (it) {
                getString(R.string.error_input_name)
            } else {
                null
            }
            tilName.error = message
        }
        viewModel.shouldCloseScreen.observe(this) {
            activity?.onBackPressed()
        }
    }

    private fun addTextChangeListeners() {
        etName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.resetErrorInputName()
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })
        etCount.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.resetErrorInputCount()
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })
    }

    private fun startEditMode(productItemId: Int?) {
        productItemId?.let { viewModel.getProductItem(it) }
        viewModel.productItem.observe(this) {
            binding.etName.setText(it.name)
            binding.etCount.setText(it.count.toString())
        }
        binding.saveButton.setOnClickListener {
            viewModel.editProductItem(etName.text?.toString(), etCount.text?.toString())
        }
    }

    private fun startAddMode() {
        saveButton.setOnClickListener {
            viewModel.addProductItem(etName.text?.toString(), etCount.text?.toString())
        }
    }

    private fun initViews(view: View) {
        tilName = view.findViewById(R.id.til_name)
        tilCount = view.findViewById(R.id.til_count)
        etName = view.findViewById(R.id.et_name)
        etCount = view.findViewById(R.id.et_count)
        saveButton = view.findViewById(R.id.save_button)
    }

    private fun parseParams(screenMode: String, productItemId: Int? = ProductItem.UNDEFINED_ID) {
        if (screenMode != MODE_ADD && screenMode != MODE_EDIT) {
            throw RuntimeException("Param screen mode is absent")
        }
        if (screenMode == MODE_EDIT && productItemId == ProductItem.UNDEFINED_ID) {
            throw RuntimeException("Param product item id is absent")
        }
    }

//    companion object {
//        private const val EXTRA_SCREEN_MODE = "extra_mode"
//        private const val EXTRA_PRODUCT_ITEM_ID = "extra_product_item-id"
//        private const val MODE_EDIT = "mode_edit"
//        private const val MODE_ADD = "mode_add"
//        private const val MODE_UNKNOWN = ""
//
//        fun newIntentAddItem(context: Context) : Intent {
//            val intent = Intent(context, ProductItemFragment::class.java)
//            intent.putExtra(EXTRA_SCREEN_MODE, MODE_ADD)
//            return intent
//        }
//
//        fun newIntentEditItem(context: Context, productItemId: Int) : Intent {
//            val intent = Intent(context, ProductItemFragment::class.java)
//            intent.putExtra(EXTRA_SCREEN_MODE, MODE_EDIT)
//            intent.putExtra(EXTRA_PRODUCT_ITEM_ID, productItemId)
//            return intent
//        }
//    }
}