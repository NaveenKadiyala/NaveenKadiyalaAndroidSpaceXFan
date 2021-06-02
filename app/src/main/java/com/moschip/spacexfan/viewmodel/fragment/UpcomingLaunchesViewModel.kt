package com.moschip.spacexfan.viewmodel.fragment

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.moschip.services.apis.RocketsApi
import com.moschip.services.helper.RetrofitCallBack
import com.moschip.services.model.UpcomingLaunch
import com.moschip.spacexfan.databinding.FragmentUpcomingLaunchesBinding
import com.moschip.spacexfan.viewmodel.BaseViewModel

class UpcomingLaunchesViewModel(application: Application) : BaseViewModel(application) {

    lateinit var binding: FragmentUpcomingLaunchesBinding

    private val launchesMutableData: MutableLiveData<List<UpcomingLaunch>> = MutableLiveData()

    val launchesLiveData: LiveData<List<UpcomingLaunch>> = launchesMutableData

    init {
        getUpcomingLaunchesApiCall()
    }

    @SuppressWarnings("unchecked")
    private fun getUpcomingLaunchesApiCall() {
        RocketsApi.getUpcomingLaunchesApi(object : RetrofitCallBack {
            override fun responseListener(response: Any?, error: String?) {
                if (error == null) {
                    launchesMutableData.postValue(response as List<UpcomingLaunch>?)
                } else {
                    Toast.makeText(getApplication(), error, Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
}