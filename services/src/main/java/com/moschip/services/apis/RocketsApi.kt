package com.moschip.services.apis

import android.annotation.SuppressLint
import com.moschip.services.helper.RetrofitCallBack
import com.moschip.services.helper.SpaceXFanClient
import com.moschip.services.model.Rocket
import com.moschip.services.model.UpcomingLaunch
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

object RocketsApi {

    @SuppressLint("CheckResult")
    fun getRocketsApi(retrofitCallBack: RetrofitCallBack) {
        SpaceXFanClient.getApi().rockets()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : Observer<List<Rocket>> {
                override fun onSubscribe(d: Disposable) {

                }

                override fun onNext(rockets: List<Rocket>) {
                    retrofitCallBack.responseListener(rockets, null)
                }

                override fun onError(e: Throwable) {
                    e.printStackTrace()
                    retrofitCallBack.responseListener(null, e.localizedMessage)
                }

                override fun onComplete() {

                }

            })
    }

    @SuppressLint("CheckResult")
    fun getUpcomingLaunchesApi(retrofitCallBack: RetrofitCallBack) {
        SpaceXFanClient.getApi().upcomingLaunches()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : Observer<List<UpcomingLaunch>> {
                override fun onSubscribe(d: Disposable) {

                }

                override fun onNext(upcomingLaunches: List<UpcomingLaunch>) {
                    retrofitCallBack.responseListener(upcomingLaunches, null)
                }

                override fun onError(e: Throwable) {
                    retrofitCallBack.responseListener(null, e.localizedMessage)
                }

                override fun onComplete() {

                }

            })
    }

}