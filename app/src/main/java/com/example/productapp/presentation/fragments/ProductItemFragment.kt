package com.example.productapp.presentation.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.productapp.R
import com.example.productapp.databinding.FragmentProductItemBinding
import com.example.productapp.domain.ProductItem
import com.example.productapp.presentation.MainActivity.Companion.EXTRA_PRODUCT_ITEM_ID
import com.example.productapp.presentation.MainActivity.Companion.EXTRA_SCREEN_MODE
import com.example.productapp.presentation.MainActivity.Companion.MODE_ADD
import com.example.productapp.presentation.MainActivity.Companion.MODE_EDIT
import com.example.productapp.presentation.ProductItemViewModel

class ProductItemFragment : Fragment() {

    private lateinit var viewModel: ProductItemViewModel
    private lateinit var binding: FragmentProductItemBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProductItemBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val screenMode = arguments?.getInt(EXTRA_SCREEN_MODE)
        val productItemId = arguments?.getInt(EXTRA_PRODUCT_ITEM_ID)
        screenMode?.let { parseParams(it, productItemId) }
        addTextChangeListeners()
        viewModel = ViewModelProvider(this).get(ProductItemViewModel::class.java)
        when (screenMode) {
            MODE_EDIT -> startEditMode(productItemId)
            MODE_ADD -> startAddMode()
        }
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.errorInputCount.observe(viewLifecycleOwner) {
            val message = if (it) {
                getString(R.string.error_input_count)
            } else {
                null
            }
            binding.tilCount.error = message
        }
        viewModel.errorInputName.observe(viewLifecycleOwner) {
            val message = if (it) {
                getString(R.string.error_input_name)
            } else {
                null
            }
            binding.tilName.error = message
        }
        viewModel.shouldCloseScreen.observe(viewLifecycleOwner) {
            activity?.onBackPressed()
        }
    }

    private fun addTextChangeListeners() {
        binding.etName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.resetErrorInputName()
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })
        binding.etCount.addTextChangedListener(object : TextWatcher {
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
        binding.tvCountInStockLabel.visibility = View.VISIBLE
        productItemId?.let { viewModel.getProductItem(it) }
        viewModel.productItem.observe(viewLifecycleOwner) {
            binding.etName.setText(it.name)
            binding.tvCountInStock.text = it.count.toString()
            binding.saveButton.setText(R.string.buy)
        }
        binding.saveButton.setOnClickListener {
            viewModel.editProductItem(binding.etName.text?.toString(),
                binding.etCount.text?.toString(), binding.tvCountInStock.text.toString())
        }
    }

    private fun startAddMode() {
        binding.tvCountInStockLabel.visibility = View.INVISIBLE
        binding.saveButton.setOnClickListener {
            viewModel.addProductItem(binding.etName.text?.toString(),
                binding.etCount.text?.toString())
        }
    }

    private fun parseParams(screenMode: Int, productItemId: Int? = ProductItem.UNDEFINED_ID) {
        if (screenMode != MODE_ADD && screenMode != MODE_EDIT) {
            throw RuntimeException("Param screen mode is absent")
        }
        if (screenMode == MODE_EDIT && productItemId == ProductItem.UNDEFINED_ID) {
            throw RuntimeException("Param product item id is absent")
        }
    }
}