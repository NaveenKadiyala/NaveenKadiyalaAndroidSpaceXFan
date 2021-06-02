package com.moschip.spacexfan.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.moschip.services.model.UpcomingLaunch
import com.moschip.spacexfan.databinding.FragmentUpcomingLaunchDetailsBinding
import com.moschip.spacexfan.helper.AppConstants
import com.moschip.spacexfan.viewmodel.fragment.UpcomingLaunchDetailsViewModel


class UpcomingLaunchDetailsFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            upcomingLaunch = it.getParcelable(AppConstants.ModelConstants.UPCOMING_LAUNCH)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUpcomingLaunchDetailsBinding.inflate(inflater, container, false).apply {
            viewModel =
                ViewModelProvider(requireActivity()).get(UpcomingLaunchDetailsViewModel::class.java)
            lifecycleOwner = viewLifecycleOwner
        }
        return binding.root
    }

    companion object {

        @JvmStatic
        fun newInstance(launch: UpcomingLaunch) =
            UpcomingLaunchDetailsFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(AppConstants.ModelConstants.UPCOMING_LAUNCH, launch)

                }
            }
    }

    private lateinit var binding: FragmentUpcomingLaunchDetailsBinding
    private var upcomingLaunch: UpcomingLaunch? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewModel?.launchMutableLiveData?.postValue(upcomingLaunch)

    }
}