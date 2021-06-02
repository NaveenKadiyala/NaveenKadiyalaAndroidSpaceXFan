package com.moschip.spacexfan.view.fragment

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.analytics.ktx.logEvent
import com.google.firebase.ktx.Firebase
import com.moschip.services.model.Rocket
import com.moschip.spacexfan.R
import com.moschip.spacexfan.adapter.ViewPagerAdapter
import com.moschip.spacexfan.databinding.FragmentRocketDetailsBinding
import com.moschip.spacexfan.helper.AppConstants
import com.moschip.spacexfan.viewmodel.fragment.RocketDetailsViewModel
import java.util.*

class RocketDetailsFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            rocket = it.getParcelable(AppConstants.ModelConstants.ROCKET)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRocketDetailsBinding.inflate(inflater, container, false).apply {
            viewModel = ViewModelProvider(requireActivity()).get(RocketDetailsViewModel::class.java)
            lifecycleOwner = viewLifecycleOwner
        }
        binding.viewModel?.binding = binding
        return binding.root
    }

    companion object {

        @JvmStatic
        fun newInstance(rocket: Rocket) =
            RocketDetailsFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(AppConstants.ModelConstants.ROCKET, rocket)
                }
            }
    }

    var rocket: Rocket? = null
    var isClicked = false
    private lateinit var binding: FragmentRocketDetailsBinding
    private var timer: Timer? = null
    private var handler: Handler? = null
    private var runnable: Runnable? = null
    private lateinit var favoriteMenuIcon: MenuItem
    private lateinit var firebaseAnalytics: FirebaseAnalytics


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewModel?.rocketLiveData?.value = rocket

        bindActionBar()

        bindViewPager()

        logFirebaseEvent()

    }

    private fun logFirebaseEvent() {
        firebaseAnalytics = Firebase.analytics
        firebaseAnalytics.logEvent(AppConstants.FirebaseEventConstants.ROCKET_DETAILS_EVENT) {
            param(AppConstants.FirebaseEventConstants.ROCKET_NAME, rocket?.name!!)
        }
    }

    private fun bindActionBar() {
        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.rocketDetailsToolbar)
        (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setHasOptionsMenu(true)
    }

    private fun bindViewPager() {
        binding.rocketDetailsViewPager.adapter = rocket?.let { ViewPagerAdapter(it.flickrImages) }
        handler = Handler(Looper.getMainLooper())
        runnable = Runnable {
            var currentPos = binding.rocketDetailsViewPager.currentItem
            binding.rocketDetailsViewPager.setCurrentItem(
                ++currentPos,
                true
            )
        }

        timer = Timer()
        timer?.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                handler?.post(runnable!!)
            }
        }, 3000, 3000)
    }

    private fun handleFavoriteIcon() {
        favoriteMenuIcon.icon = if (rocket?.isFavorite == true) ContextCompat.getDrawable(
            requireContext(),
            R.drawable.ic_bottom_favorite_selected
        ) else ContextCompat.getDrawable(
            requireContext(),
            R.drawable.ic_bottom_favorite_un_selected
        )
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        requireActivity().menuInflater.inflate(R.menu.menu_action, menu)
        favoriteMenuIcon = menu.findItem(R.id.action_favorite)
        handleFavoriteIcon()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_favorite -> {
                isClicked = true
                rocket?.isFavorite = !rocket?.isFavorite!!
                handleFavoriteIcon()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        runnable?.let { handler?.removeCallbacks(it) }
        timer?.cancel()
        super.onDestroy()
    }

}