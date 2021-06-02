package com.moschip.spacexfan.view.activity

import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.moschip.spacexfan.R
import com.moschip.spacexfan.databinding.ActivityFragmentLoaderBinding
import com.moschip.spacexfan.helper.AppConstants
import com.moschip.spacexfan.view.fragment.RocketDetailsFragment
import com.moschip.spacexfan.view.fragment.UpcomingLaunchDetailsFragment

class FragmentLoaderActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFragmentLoaderBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFragmentLoaderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (intent.getStringExtra(AppConstants.Defaults.FROM) == AppConstants.FragmentConstants.ROCKET_DETAILS) {
            binding.fragmentLoaderToolbar.visibility = GONE
            replaceFragment(
                RocketDetailsFragment.newInstance(intent.getParcelableExtra(AppConstants.ModelConstants.ROCKET)!!),
                AppConstants.FragmentConstants.ROCKET_DETAILS
            )
        } else if (intent.getStringExtra(AppConstants.Defaults.FROM) == AppConstants.FragmentConstants.LAUNCH_DETAILS) {
            binding.fragmentLoaderToolbar.visibility = VISIBLE
            setSupportActionBar(binding.fragmentLoaderToolbar)
            supportActionBar?.title = AppConstants.FragmentConstants.LAUNCH_DETAILS
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            replaceFragment(
                UpcomingLaunchDetailsFragment.newInstance(intent.getParcelableExtra(AppConstants.ModelConstants.UPCOMING_LAUNCH)!!),
                AppConstants.FragmentConstants.LAUNCH_DETAILS
            )
        }
    }

    private fun replaceFragment(fragment: Fragment, tag: String) {
        supportFragmentManager
            .beginTransaction()
            .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
            .replace(binding.fragmentLoaderContainer.id, fragment, tag)
            .addToBackStack(tag)
            .commit()
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount == 1) {
            val fragment =
                supportFragmentManager.findFragmentById(binding.fragmentLoaderContainer.id)
            if (fragment is RocketDetailsFragment) {
                val intent = intent
                intent.putExtra(AppConstants.Defaults.IS_FAV, fragment.rocket?.isFavorite)
                intent.putExtra(AppConstants.Defaults.IS_CLICKED, fragment.isClicked)
                intent.putExtra(
                    AppConstants.Defaults.POSITION,
                    intent.getIntExtra(AppConstants.Defaults.POSITION, 0)
                )
                setResult(RESULT_OK, intent)
            }
            finish()
            overridePendingTransition(R.anim.pop_enter, R.anim.pop_exit)
        } else
            super.onBackPressed()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}