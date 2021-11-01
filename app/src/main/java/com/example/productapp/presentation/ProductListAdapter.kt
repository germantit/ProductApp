package com.example.productapp.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.productapp.R
import com.example.productapp.domain.ProductItem

class ProductListAdapter: RecyclerView.Adapter<ProductListAdapter.ProductItemViewHolder>() {

    var productList = listOf<ProductItem>()
    set(value) {
        field = value
        notifyDataSetChanged()
    }

    var onProductLongClickListener: ((ProductItem) -> Unit)? = null
    var onProductClickListener: ((ProductItem) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductItemViewHolder {
        val layout = when (viewType) {
            VIEW_DISABLED -> R.layout.product_item_disabled
            VIEW_ENABLED -> R.layout.product_item_enabled
            else -> throw RuntimeException("Unknown view type: $viewType")
        }
        val view = LayoutInflater.from(parent.context).inflate(layout, parent, false)
        return ProductItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductItemViewHolder, position: Int) {
        val productItem = productList[position]
        holder.tvName.text = productItem.name
        holder.tvCount.text = productItem.count.toString()
        holder.itemView.setOnLongClickListener {
            onProductLongClickListener?.invoke(productItem)
            true
        }
        holder.itemView.setOnClickListener {
            onProductClickListener?.invoke(productItem)
        }
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    override fun getItemViewType(position: Int): Int {
        val item = productList[position]
        return if (item.enabled) {
            VIEW_ENABLED
        } else {
            VIEW_DISABLED
        }
    }

    class ProductItemViewHolder(view: View): RecyclerView.ViewHolder(view){
        val tvName: TextView = view.findViewById(R.id.tv_name)
        val tvCount: TextView = view.findViewById(R.id.tv_count)
    }

    companion object {
        const val VIEW_ENABLED = 100
        const val VIEW_DISABLED = 101

        const val MAX_POOL_SIZE = 30
    }

}