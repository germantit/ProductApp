package com.example.productapp.presentation.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.ViewModelProvider
import com.example.productapp.R
import com.example.productapp.domain.ProductItem
import com.example.productapp.presentation.ProductItemActivity
import com.example.productapp.presentation.ProductItemViewModel
import com.google.android.material.textfield.TextInputLayout

class ProductItemFragment(
    private val screenMode : String = MODE_UNKNOWN,
    private val productItemId : Int = ProductItem.UNDEFINED_ID
) : Fragment() {

    private lateinit var viewModel: ProductItemViewModel

    private lateinit var tilName: TextInputLayout
    private lateinit var tilCount: TextInputLayout
    private lateinit var etName: EditText
    private lateinit var etCount: EditText
    private lateinit var buttonSave: Button

    fun newInstanceAddItem() : ProductItemFragment {
        return ProductItemFragment(MODE_ADD)
    }

    fun newInstanceEditItem(productItemId: Int) : ProductItemFragment {
        return ProductItemFragment(MODE_EDIT, productItemId)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_product_item, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        parseParams()
        viewModel = ViewModelProvider(this).get(ProductItemViewModel::class.java)
        initViews(view)
        addTextChangeListeners()
        when (screenMode) {
            MODE_EDIT -> startEditMode()
            MODE_ADD -> startAddMode()
        }
        observeViewModel()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    private fun observeViewModel() {
        viewModel.errorInputCount.observe(viewLifecycleOwner) {
            val message = if (it) {
                getString(R.string.error_input_count)
            } else {
                null
            }
            tilCount.error = message
        }
        viewModel.errorInputName.observe(viewLifecycleOwner) {
            val message = if (it) {
                getString(R.string.error_input_name)
            } else {
                null
            }
            tilName.error = message
        }
        viewModel.shouldCloseScreen.observe(viewLifecycleOwner) {
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

    private fun startEditMode() {
        viewModel.getProductItem(productItemId)
        viewModel.productItem.observe(viewLifecycleOwner) {
            etName.setText(it.name)
            etCount.setText(it.count.toString())
        }
        buttonSave.setOnClickListener {
            viewModel.editProductItem(etName.text?.toString(), etCount.text?.toString())
        }
    }

    private fun startAddMode() {
        buttonSave.setOnClickListener {
            viewModel.addProductItem(etName.text?.toString(), etCount.text?.toString())
        }
    }

    private fun initViews(view: View) {
        tilName = view.findViewById(R.id.til_name)
        tilCount = view.findViewById(R.id.til_count)
        etName = view.findViewById(R.id.et_name)
        etCount = view.findViewById(R.id.et_count)
        buttonSave = view.findViewById(R.id.save_button)
    }

    private fun parseParams() {
        if (screenMode != MODE_ADD && screenMode != MODE_EDIT) {
            throw RuntimeException("Param screen mode is absent")
        }
        if (screenMode == MODE_EDIT && productItemId == ProductItem.UNDEFINED_ID) {
            throw RuntimeException("Param product item id is absent")
        }
    }

    companion object {
        private const val EXTRA_SCREEN_MODE = "extra_mode"
        private const val EXTRA_PRODUCT_ITEM_ID = "extra_product_item-id"
        private const val MODE_EDIT = "mode_edit"
        private const val MODE_ADD = "mode_add"
        private const val MODE_UNKNOWN = ""

        fun newIntentAddItem(context: Context) : Intent {
            val intent = Intent(context, ProductItemActivity::class.java)
            intent.putExtra(EXTRA_SCREEN_MODE, MODE_ADD)
            return intent
        }

        fun newIntentEditItem(context: Context, productItemId: Int) : Intent {
            val intent = Intent(context, ProductItemActivity::class.java)
            intent.putExtra(EXTRA_SCREEN_MODE, MODE_EDIT)
            intent.putExtra(EXTRA_PRODUCT_ITEM_ID, productItemId)
            return intent
        }
    }
}