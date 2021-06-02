package com.moschip.services.helper

import android.app.Application

open class ServicesAppController : Application() {

    companion object {
        private lateinit var serivcesController: ServicesAppController
        fun getInstance() = serivcesController
    }

    override fun onCreate() {
        serivcesController = this
        super.onCreate()
    }
}