package com.example.productapp.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.productapp.R
import com.example.productapp.databinding.FragmentProductListBinding
import com.example.productapp.presentation.MainActivity
import com.example.productapp.presentation.ProductListAdapter
import com.example.productapp.presentation.ProductViewModel

class ProductListFragment : Fragment() {

    private lateinit var viewModel: ProductViewModel
    private lateinit var adapter: ProductListAdapter
    private lateinit var binding: FragmentProductListBinding

    private val bundle = Bundle()
    private val fragmentItem = ProductItemFragment()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = FragmentProductListBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView(view)
        viewModel = ViewModelProvider(this).get(ProductViewModel::class.java)
        viewModel.productList.observe(viewLifecycleOwner) {
            adapter.productList = it
        }
        binding.buttonAddProductItem.setOnClickListener {
            bundle.putInt(MainActivity.EXTRA_SCREEN_MODE, MainActivity.MODE_ADD)
            fragmentItem.arguments = bundle
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.product_list_container, fragmentItem)
                .addToBackStack("main_fragment")
                .commit()
        }
    }

    private fun setupRecyclerView(view: View) {
        val rvProductList = view.findViewById<RecyclerView>(R.id.rv_product_list)
        adapter = ProductListAdapter()
        rvProductList.adapter = adapter
        adapter.onProductLongClickListener = {
            viewModel.deleteProductItem(it)
        }
        setupClickListener()
    }

    private fun setupClickListener() {
        adapter.onProductClickListener = {
            bundle.putInt(MainActivity.EXTRA_SCREEN_MODE, MainActivity.MODE_EDIT)
            bundle.putInt(MainActivity.EXTRA_PRODUCT_ITEM_ID, it.id)
            fragmentItem.arguments = bundle
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.product_list_container, fragmentItem)
                .addToBackStack("main_fragment")
                .commit()
        }
    }
}