package com.example.productapp.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.productapp.R
import com.example.productapp.domain.ProductItem

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: ProductViewModel
    private lateinit var adapter: ProductListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupRecyclerView()
        viewModel = ViewModelProvider(this).get(ProductViewModel::class.java)
        viewModel.productList.observe(this) {
            adapter.productList = it
        }
    }

    private fun setupRecyclerView() {
        val rvProductList = findViewById<RecyclerView>(R.id.rv_product_list)
        adapter = ProductListAdapter()
        rvProductList.adapter = adapter
        adapter.onProductLongClickListener = object : ProductListAdapter.OnProductLongClickListener {
            override fun onProductLongClick(productItem: ProductItem) {
                viewModel.deleteProductItem(productItem)
            }
        }
    }
}