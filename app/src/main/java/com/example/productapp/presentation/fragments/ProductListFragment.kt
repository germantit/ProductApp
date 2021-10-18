package com.example.productapp.presentation.fragments

import android.graphics.Insets.add
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.productapp.R
import com.example.productapp.presentation.MainActivity
import com.example.productapp.presentation.ProductListAdapter
import com.example.productapp.presentation.ProductViewModel

class ProductListFragment : Fragment() {

    private lateinit var viewModel: ProductViewModel
    private lateinit var adapter: ProductListAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_product_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView(view)
        viewModel = ViewModelProvider(this).get(ProductViewModel::class.java)
        viewModel.productList.observe(viewLifecycleOwner) {
            adapter.productList = it
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
            val bundle = Bundle()
            val fragmentItem = ProductItemFragment()
            bundle.putString(MainActivity.EXTRA_SCREEN_MODE, MainActivity.MODE_EDIT)
            bundle.putString(MainActivity.EXTRA_PRODUCT_ITEM_ID, it.id.toString())
            fragmentItem.arguments = bundle
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.product_list_container, fragmentItem)
                .commit()
        }
    }
}