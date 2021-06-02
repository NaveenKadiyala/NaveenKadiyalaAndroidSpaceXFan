package com.moschip.spacexfan.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.moschip.services.model.Rocket
import com.moschip.spacexfan.databinding.ItemRocketBinding

class RocketsAdapter(private var rockets: List<Rocket>) :
    RecyclerView.Adapter<RocketsAdapter.RocketsViewHolder>() {

    interface RocketClickListener {

        fun onRocketClick(position: Int)

        fun onFavoriteClick(position: Int)
    }

    private lateinit var rocketClickListener: RocketClickListener

    fun setRocketClickListener(rocketClickListener: RocketClickListener) {
        this.rocketClickListener = rocketClickListener
    }

    inner class RocketsViewHolder(binding: ItemRocketBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private val itemBinding: ItemRocketBinding = binding
        fun bind(pos: Int) {
            itemBinding.model = rockets[pos]
            itemBinding.pos = pos
            itemBinding.rocketClickListener = rocketClickListener
            itemBinding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RocketsViewHolder {
        return RocketsViewHolder(
            ItemRocketBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RocketsViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int = rockets.size
}