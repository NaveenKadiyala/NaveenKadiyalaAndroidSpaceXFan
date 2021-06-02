package com.moschip.spacexfan.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.moschip.spacexfan.helper.AppConstants

open class BaseViewModel(application: Application) : AndroidViewModel(application){
    protected val observerEvents = MutableLiveData<AppConstants.ObserverEvents>()
    val getObservableEvents: LiveData<AppConstants.ObserverEvents>
        get() = observerEvents
}