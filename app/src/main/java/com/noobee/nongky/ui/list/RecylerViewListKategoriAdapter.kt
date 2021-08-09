package com.noobee.nongky.ui.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.noobee.nongky.databinding.ItemListTempatMakanBinding
import com.noobee.nongky.model.DataCafe

class RecylerViewListKategoriAdapter(
    val listKategoriViewModel: ListKategoriViewModel
): RecyclerView.Adapter<RecylerViewListKategoriAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemListTempatMakanBinding) : RecyclerView.ViewHolder(binding.root)

    var listItem : List<DataCafe>? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecylerViewListKategoriAdapter.ViewHolder {
        return ViewHolder(
            ItemListTempatMakanBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(
        holder: RecylerViewListKategoriAdapter.ViewHolder,
        itemPosition: Int
    ) {
        val currentItem = listItem?.get(itemPosition)

        holder.binding.apply {
            viewModel = listKategoriViewModel
            position = itemPosition
            item = currentItem
        }

    }

    override fun getItemCount(): Int = listItem?.size ?: 0
}