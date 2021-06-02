package com.moschip.spacexfan.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.moschip.spacexfan.databinding.ItemViewPagerBinding

class ViewPagerAdapter(private var images: List<String>) :
    RecyclerView.Adapter<ViewPagerAdapter.ViewPagerViewHolder>() {
    inner class ViewPagerViewHolder(binding: ItemViewPagerBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val itemBinding: ItemViewPagerBinding = binding

        fun bind(image: String) {
            itemBinding.image = image
            itemBinding.executePendingBindings()
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewPagerViewHolder {
        return ViewPagerViewHolder(
            ItemViewPagerBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewPagerViewHolder, position: Int) {
        holder.bind(images[position % images.size])
    }

    override fun getItemCount(): Int = Int.MAX_VALUE
}