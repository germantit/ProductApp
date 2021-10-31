package com.example.productapp.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.productapp.R
import com.example.productapp.presentation.fragments.ProductListFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val fragment = ProductListFragment()
        fragment.arguments = null
        supportFragmentManager.beginTransaction()
            .add(R.id.product_list_container, fragment)
            .commit()
    }

    companion object {
        const val EXTRA_SCREEN_MODE = "extra_mode"
        const val EXTRA_PRODUCT_ITEM_ID = "extra_product_item-id"
        const val MODE_EDIT = 1
        const val MODE_ADD = 0
        const val MODE_BUY = 2
        const val MODE_UNKNOWN = ""
    }
}