//package com.example.productapp.presentation.fragments
//
//import android.os.Bundle
//import androidx.fragment.app.Fragment
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import androidx.lifecycle.ViewModelProvider
//import androidx.recyclerview.widget.RecyclerView
//import com.example.productapp.R
//import com.example.productapp.domain.ProductItem
//import com.example.productapp.presentation.ProductListAdapter
//import com.example.productapp.presentation.ProductViewModel
//
//class ProductListFragment : Fragment() {
//
//    private lateinit var viewModel: ProductViewModel
//    private lateinit var adapter: ProductListAdapter
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//    }
//
//    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
//                              savedInstanceState: Bundle?): View? {
//        return inflater.inflate(R.layout.fragment_product_list, container, false)
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        setupRecyclerView(view)
//        viewModel = ViewModelProvider(this).get(ProductViewModel::class.java)
//        viewModel.productList.observe(viewLifecycleOwner) {
//            adapter.productList = it
//        }
//    }
//
//    private fun setupRecyclerView(view: View) {
//        val rvProductList = view.findViewById<RecyclerView>(R.id.rv_product_list)
//        adapter = ProductListAdapter()
//        rvProductList.adapter = adapter
//        adapter.onProductLongClickListener = object : ProductListAdapter.OnProductLongClickListener{
//            override fun onProductLongClick(productItem: ProductItem) {
//                viewModel.deleteProductItem(productItem)
//            }
//        }
//    }
//}