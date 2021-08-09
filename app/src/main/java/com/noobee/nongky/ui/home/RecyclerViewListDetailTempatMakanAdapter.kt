package com.noobee.nongky.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.noobee.nongky.databinding.ItemDetailTempatMakanBinding
import com.noobee.nongky.model.DataCafe

class RecyclerViewListDetailTempatMakanAdapter(
    val homeViewModel: HomeViewModel
) : RecyclerView.Adapter<RecyclerViewListDetailTempatMakanAdapter.ViewHolder>() {

    var listItem: List<DataCafe>? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerViewListDetailTempatMakanAdapter.ViewHolder {
        return ViewHolder(
            ItemDetailTempatMakanBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(
        holder: RecyclerViewListDetailTempatMakanAdapter.ViewHolder,
        itemPosition: Int
    ) {
        val currentItem = listItem?.get(itemPosition)

        holder.binding.apply {
            viewModel = homeViewModel
            position = itemPosition
            item = currentItem
        }
    }

    override fun getItemCount(): Int = listItem?.size ?: 0

    inner class ViewHolder(val binding: ItemDetailTempatMakanBinding) :
        RecyclerView.ViewHolder(binding.root)
}