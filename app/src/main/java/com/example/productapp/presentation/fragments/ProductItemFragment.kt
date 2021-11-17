package com.example.productapp.presentation.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.productapp.R
import com.example.productapp.databinding.FragmentProductItemBinding
import com.example.productapp.domain.ProductItem
import com.example.productapp.domain.UniqueProduct
import com.example.productapp.presentation.MainActivity.Companion.EXTRA_PRODUCT_ITEM_ID
import com.example.productapp.presentation.MainActivity.Companion.EXTRA_SCREEN_MODE
import com.example.productapp.presentation.MainActivity.Companion.MODE_ADD
import com.example.productapp.presentation.MainActivity.Companion.MODE_EDIT
import com.example.productapp.presentation.ProductItemViewModel
import com.example.productapp.presentation.SearchAdapter
import java.util.*

class ProductItemFragment : Fragment() {

    private lateinit var viewModel: ProductItemViewModel
    private lateinit var searchAdapter: SearchAdapter
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
        binding.rvSearchName.layoutManager = GridLayoutManager(context, 2)
        searchAdapter = SearchAdapter()
        binding.rvSearchName.adapter = searchAdapter
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
        binding.tvMode.text = getString(R.string.edit_mode)
        productItemId?.let { viewModel.getProductItem(it) }
        viewModel.productItem.observe(viewLifecycleOwner) {
            binding.etName.setText(it.name)
            binding.etCount.setText(it.count.toString())
        }
        binding.saveButton.setOnClickListener {
            viewModel.editProductItem(binding.etName.text?.toString(),
                binding.etCount.text?.toString())
        }
    }

    private fun startAddMode() {
        binding.etName.setText(DEFAULT_NAME)
        binding.etCount.setText(DEFAULT_COUNT)
        binding.tvMode.text = getString(R.string.add_mode)
        binding.etName.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s != null && s.length > 1) {
                    val searchQuery = s.toString()

                    viewModel.searchDatabase(searchQuery).observe(viewLifecycleOwner, { list ->
                        list.let {
                            Collections.sort(it)
                            searchAdapter.setData(it)
                        }
                    })
                } else {
                    searchAdapter.setData(DEF_LIST)
                }
                searchAdapter.onProductClickListener = {
                    binding.etName.setText(it.item)
                    searchAdapter.setData(DEF_LIST)
                }
            }

            override fun afterTextChanged(s: Editable?) {
            }

        })
        binding.saveButton.setOnClickListener {
            viewModel.addProductItem(binding.etName.text?.toString(),
                binding.etCount.text?.toString())
            viewModel.addUniqueProduct((highRegister(binding.etName.text?.toString())),
                binding.etCount.text?.toString()
            )
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

    private fun highRegister(item: String?): String {
        return item?.substring(0,1)?.uppercase(Locale.getDefault()) + item?.substring(1)
            ?.lowercase(Locale.getDefault())
    }

    companion object {
        private const val DEFAULT_NAME = ""
        private const val DEFAULT_COUNT = ""
        private val DEF_LIST = emptyList<UniqueProduct>()
    }
}