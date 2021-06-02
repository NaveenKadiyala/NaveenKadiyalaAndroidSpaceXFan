package com.moschip.spacexfan.view.activity

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.moschip.services.model.Rocket
import com.moschip.spacexfan.R
import com.moschip.spacexfan.databinding.ActivityMainBinding
import com.moschip.spacexfan.helper.AppConstants
import com.moschip.spacexfan.view.fragment.FavoritesFragment
import com.moschip.spacexfan.view.fragment.RocketsFragment
import com.moschip.spacexfan.view.fragment.UpcomingLaunchesFragment

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    private lateinit var binding: ActivityMainBinding
    private var favoriteRockets = arrayListOf<Rocket>()
    private val rocketsFragment by lazy { RocketsFragment() }
    private val upcomingLaunchesFragment by lazy { UpcomingLaunchesFragment() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomNavigationView.itemIconTintList = null
        binding.bottomNavigationView.setOnNavigationItemSelectedListener(this)
        binding.bottomNavigationView.selectedItemId = R.id.rockets

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.rockets -> {
                replaceFragment(rocketsFragment, AppConstants.FragmentConstants.ROCKETS)
            }
            R.id.favorite -> {
                replaceFragment(
                    FavoritesFragment.newInstance(favoriteRockets),
                    AppConstants.FragmentConstants.FAVORITES
                )
            }
            R.id.launches -> {
                replaceFragment(upcomingLaunchesFragment, AppConstants.FragmentConstants.LAUNCHES)
            }
        }
        return true
    }

    fun updateFavoriteRockets(rocket: Rocket) {
        if (favoriteRockets.contains(rocket)) {
            favoriteRockets.remove(rocket)
        } else
            favoriteRockets.add(rocket)
    }

    private fun replaceFragment(fragment: Fragment, tag: String) {
        supportFragmentManager
            .beginTransaction()
            .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
            .replace(binding.fragmentContainer.id, fragment, tag)
            .addToBackStack(tag)
            .commit()
    }

    override fun onBackPressed() {
        val currentFragment = supportFragmentManager.findFragmentById(binding.fragmentContainer.id)
        if (currentFragment is RocketsFragment) {
            finish()
        } else {
            super.onBackPressed()
        }
    }

    fun setNavigationItemSelected(menuItem: Int) {
        binding.bottomNavigationView.menu.findItem(menuItem).isChecked = true
    }
}