package com.moschip.spacexfan.viewmodel.fragment

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.moschip.services.model.Rocket
import com.moschip.spacexfan.databinding.FragmentRocketDetailsBinding
import com.moschip.spacexfan.viewmodel.BaseViewModel

class RocketDetailsViewModel(application: Application) : BaseViewModel(application) {

    lateinit var binding: FragmentRocketDetailsBinding

    var rocketLiveData: MutableLiveData<Rocket> = MutableLiveData()
}