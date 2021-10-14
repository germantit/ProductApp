package com.example.productapp.presentation

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.productapp.R
import com.example.productapp.presentation.fragments.ProductItemFragment
import com.google.android.material.floatingactionbutton.FloatingActionButton

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
        val buttonAddItem = findViewById<FloatingActionButton>(R.id.button_add_product_item)
        buttonAddItem.setOnClickListener {
            val intent = ProductItemFragment.newIntentAddItem(this)
            startActivity(intent)
        }
    }

    private fun setupRecyclerView() {
        val rvProductList = findViewById<RecyclerView>(R.id.rv_product_list)
        adapter = ProductListAdapter()
        rvProductList.adapter = adapter
        adapter.onProductLongClickListener = {
            viewModel.deleteProductItem(it)
        }
        setupClickListener()
    }

    private fun setupClickListener() {
        adapter.onProductClickListener = {
            Log.d("MainActivity", it.toString())
            val intent = ProductItemFragment.newIntentEditItem(this, it.id)
            startActivity(intent)
        }
    }
}