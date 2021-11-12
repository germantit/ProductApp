package com.example.productapp.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.productapp.R
import com.example.productapp.domain.UniqueProduct

class SearchAdapter: RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {

    private var oldData = emptyList<UniqueProduct>()

    var onProductClickListener: ((UniqueProduct) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        return SearchViewHolder(LayoutInflater.from(parent.context).inflate(
            R.layout.search_item,
            parent,
            false
        ))
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.tvSearch.text = oldData[position].item
        holder.itemView.setOnClickListener {
            onProductClickListener?.invoke(oldData[position])
        }
    }

    override fun getItemCount(): Int {
        return oldData.size
    }

    class SearchViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvSearch: TextView = view.findViewById(R.id.tvSearchName)
    }

    fun setData(newData: List<UniqueProduct>){
        oldData = newData
        notifyDataSetChanged()
    }

}