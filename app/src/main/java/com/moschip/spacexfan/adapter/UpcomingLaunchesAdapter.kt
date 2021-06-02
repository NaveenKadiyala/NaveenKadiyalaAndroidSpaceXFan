package com.moschip.spacexfan.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.moschip.services.model.UpcomingLaunch
import com.moschip.spacexfan.databinding.ItemUpcomingLaunchBinding

class UpcomingLaunchesAdapter(private var launches: List<UpcomingLaunch>) :
    RecyclerView.Adapter<UpcomingLaunchesAdapter.UpcomingLaunchesViewHolder>() {

    interface LaunchClickListener {
        fun onLaunchClick(model: UpcomingLaunch)
    }

    private lateinit var launchClickListener: LaunchClickListener

    fun setLaunchClickListener(launchClickListener: LaunchClickListener) {
        this.launchClickListener = launchClickListener
    }

    inner class UpcomingLaunchesViewHolder(binding: ItemUpcomingLaunchBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private val itemBinding: ItemUpcomingLaunchBinding = binding
        fun bind(pos: Int) {
            itemBinding.model = launches[pos]
            itemBinding.launchClickListener = launchClickListener
            itemBinding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UpcomingLaunchesViewHolder {
        return UpcomingLaunchesViewHolder(
            ItemUpcomingLaunchBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: UpcomingLaunchesViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int = launches.size
}