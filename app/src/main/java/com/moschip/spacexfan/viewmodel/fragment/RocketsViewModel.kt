package com.moschip.spacexfan.viewmodel.fragment

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.moschip.services.apis.RocketsApi
import com.moschip.services.helper.RetrofitCallBack
import com.moschip.services.model.Rocket
import com.moschip.spacexfan.databinding.FragmentRocketsBinding
import com.moschip.spacexfan.viewmodel.BaseViewModel

class RocketsViewModel(application: Application) : BaseViewModel(application) {

    lateinit var binding: FragmentRocketsBinding

    private val rocketsMutableData: MutableLiveData<List<Rocket>> = MutableLiveData()

    val rocketsLiveData: LiveData<List<Rocket>> = rocketsMutableData

    init {
        getRocketsApiCall()
    }

    @SuppressWarnings("unchecked")
    private fun getRocketsApiCall() {
        RocketsApi.getRocketsApi(object : RetrofitCallBack {
            override fun responseListener(response: Any?, error: String?) {
                if (error == null) {
                    rocketsMutableData.postValue(response as List<Rocket>?)
                } else {
                    Toast.makeText(getApplication(), error, Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
}