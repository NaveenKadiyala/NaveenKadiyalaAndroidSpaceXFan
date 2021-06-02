package com.moschip.spacexfan.viewmodel.fragment

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.moschip.services.model.UpcomingLaunch
import com.moschip.spacexfan.databinding.FragmentUpcomingLaunchDetailsBinding
import com.moschip.spacexfan.viewmodel.BaseViewModel

class UpcomingLaunchDetailsViewModel(application: Application) : BaseViewModel(application) {

    lateinit var binding: FragmentUpcomingLaunchDetailsBinding

    var launchMutableLiveData: MutableLiveData<UpcomingLaunch> = MutableLiveData()
}