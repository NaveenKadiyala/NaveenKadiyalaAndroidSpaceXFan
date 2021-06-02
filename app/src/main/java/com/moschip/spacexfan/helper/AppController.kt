package com.moschip.spacexfan.helper

import com.moschip.services.helper.ServicesAppController

class AppController : ServicesAppController() {

    companion object {
        private lateinit var appController: AppController
        fun getInstance() = appController
    }

    override fun onCreate() {
        appController = this
        super.onCreate()
    }
}