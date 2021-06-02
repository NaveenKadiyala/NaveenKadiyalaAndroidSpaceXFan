package com.moschip.spacexfan.viewmodel.fragment

import android.app.Application
import android.view.View
import com.moschip.spacexfan.databinding.FragmentFavoritesBinding
import com.moschip.spacexfan.viewmodel.BaseViewModel

class FavoritesViewModel(application: Application) : BaseViewModel(application) {

     lateinit var binding: FragmentFavoritesBinding

    fun handleVisibility(size: Int) {
        if (size == 0) {
            binding.noFavView.visibility = View.VISIBLE
            binding.favoriteRocketsRv.visibility = View.GONE
        } else {
            binding.noFavView.visibility = View.GONE
            binding.favoriteRocketsRv.visibility = View.VISIBLE
        }
    }

}