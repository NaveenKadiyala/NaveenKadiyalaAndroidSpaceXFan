package com.moschip.spacexfan.viewmodel.activity

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.app.Application
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.animation.LinearInterpolator
import com.moschip.spacexfan.databinding.ActivitySplashBinding
import com.moschip.spacexfan.helper.AppConstants
import com.moschip.spacexfan.viewmodel.BaseViewModel

class SplashViewModel(application: Application) : BaseViewModel(application) {

    lateinit var splashBinding: ActivitySplashBinding

     fun playZoomInAnimation() {
        val view = splashBinding.splashImgView
        val sX = ObjectAnimator.ofFloat(view, View.SCALE_X, 0.4f, 1f)
        val sY = ObjectAnimator.ofFloat(view, View.SCALE_Y, 0.4f, 1f)
        val zoomAnimatorSet = AnimatorSet()
        zoomAnimatorSet.playTogether(sX, sY)
        zoomAnimatorSet.interpolator = LinearInterpolator()
        zoomAnimatorSet.duration = AppConstants.Defaults.SPLASH_ANIM_TIME
        zoomAnimatorSet.setTarget(view)
        zoomAnimatorSet.start()
        Handler(Looper.getMainLooper()).postDelayed({
            observerEvents.postValue(AppConstants.ObserverEvents.OPEN_MAIN_SCREEN)
        }, AppConstants.Defaults.SPLASH_ANIM_TIME)
    }

}