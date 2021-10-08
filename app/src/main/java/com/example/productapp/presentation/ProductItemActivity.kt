package com.example.productapp.presentation

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.productapp.R

class ProductItemActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_item)
        val mode = intent.getStringExtra(EXTRA_SCREEN_MODE)
        Log.d("ProductItemActivity", mode.toString())
    }

    companion object {
        private const val EXTRA_SCREEN_MODE = "extra_mode"
        private const val EXTRA_PRODUCT_ITEM_ID = "extra_product_item-id"
        private const val MODE_EDIT = "mode_edit"
        private const val MODE_ADD = "mode_add"

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