package com.moschip.spacexfan.view.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.moschip.services.model.UpcomingLaunch
import com.moschip.spacexfan.R
import com.moschip.spacexfan.adapter.UpcomingLaunchesAdapter
import com.moschip.spacexfan.databinding.FragmentUpcomingLaunchesBinding
import com.moschip.spacexfan.helper.AppConstants
import com.moschip.spacexfan.view.activity.FragmentLoaderActivity
import com.moschip.spacexfan.view.activity.MainActivity
import com.moschip.spacexfan.viewmodel.fragment.UpcomingLaunchesViewModel


class UpcomingLaunchesFragment : Fragment() {

    private lateinit var binding: FragmentUpcomingLaunchesBinding
    private lateinit var adapter: UpcomingLaunchesAdapter
    private var launches = arrayListOf<UpcomingLaunch>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUpcomingLaunchesBinding.inflate(inflater, container, false).apply {
            viewModel =
                ViewModelProvider(requireActivity()).get(UpcomingLaunchesViewModel::class.java)
            lifecycleOwner = viewLifecycleOwner
        }
        binding.viewModel?.binding = binding
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (requireActivity() as MainActivity).setNavigationItemSelected(R.id.launches)

        binding.viewModel?.launchesLiveData?.observe(requireActivity(), {
            if (it != null) {
                launches = it as ArrayList<UpcomingLaunch>
                bindRecyclerView()
            }
        })
    }

    private fun bindRecyclerView() {
        binding.launchesProgressView.root.visibility = View.GONE
        adapter = UpcomingLaunchesAdapter(launches)
        adapter.setLaunchClickListener(rocketClickListener)
        binding.launchesRv.adapter = adapter
    }

    private val rocketClickListener = object : UpcomingLaunchesAdapter.LaunchClickListener {
        override fun onLaunchClick(model: UpcomingLaunch) {
            val intent = Intent(requireContext(), FragmentLoaderActivity::class.java)
            intent.putExtra(
                AppConstants.Defaults.FROM,
                AppConstants.FragmentConstants.LAUNCH_DETAILS
            )
            intent.putExtra(AppConstants.ModelConstants.UPCOMING_LAUNCH, model)
            startActivity(intent)
            requireActivity().overridePendingTransition(
                R.anim.enter,
                R.anim.exit,
            )
        }
    }
}