package com.moschip.spacexfan.view.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.moschip.spacexfan.R
import com.moschip.spacexfan.databinding.ActivitySplashBinding
import com.moschip.spacexfan.helper.AppConstants
import com.moschip.spacexfan.viewmodel.activity.SplashViewModel

class SplashActivity : AppCompatActivity() {

    private lateinit var splashBinding: ActivitySplashBinding
    private lateinit var splashViewModel: SplashViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        splashBinding = ActivitySplashBinding.inflate(layoutInflater).apply {
            splashViewModel =
                ViewModelProvider(this@SplashActivity).get(SplashViewModel::class.java)
            splashViewModel.splashBinding = this
        }

        setContentView(splashBinding.root)

        splashViewModel.getObservableEvents.observe(this, Observer {
            val event = it.takeUnless { it == null } ?: return@Observer
            performActionBasedOnEvent(event)
        })

        splashViewModel.playZoomInAnimation()
    }

    /**
     * This function is responsible for performing actions based on event observed from splash view model
     */
    @SuppressLint("InlinedApi")
    private fun performActionBasedOnEvent(event: AppConstants.ObserverEvents) {
        when (event) {
            AppConstants.ObserverEvents.OPEN_MAIN_SCREEN -> {
                startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                overridePendingTransition(R.anim.enter, R.anim.exit)
                finish()
            }
        }
    }
}