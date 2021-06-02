package com.moschip.spacexfan.view.fragment

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.moschip.services.model.Rocket
import com.moschip.spacexfan.R
import com.moschip.spacexfan.adapter.RocketsAdapter
import com.moschip.spacexfan.databinding.FragmentRocketsBinding
import com.moschip.spacexfan.helper.AppConstants
import com.moschip.spacexfan.view.activity.FragmentLoaderActivity
import com.moschip.spacexfan.view.activity.MainActivity
import com.moschip.spacexfan.viewmodel.fragment.RocketsViewModel

class RocketsFragment : Fragment() {

    private lateinit var binding: FragmentRocketsBinding
    private var rockets = arrayListOf<Rocket>()
    private lateinit var adapter: RocketsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRocketsBinding.inflate(inflater, container, false).apply {
            viewModel = ViewModelProvider(requireActivity()).get(RocketsViewModel::class.java)
            lifecycleOwner = viewLifecycleOwner
        }
        binding.viewModel?.binding = binding
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (requireActivity() as MainActivity).setNavigationItemSelected(R.id.rockets)

        binding.viewModel?.rocketsLiveData?.observe(requireActivity(), {
            if (it != null) {
                rockets = it as ArrayList<Rocket>
                bindRecyclerView()
            }
        })

    }

    private fun bindRecyclerView() {
        binding.rocketsProgressView.root.visibility = View.GONE
        adapter = RocketsAdapter(rockets)
        adapter.setRocketClickListener(rocketClickListener)
        binding.rocketsRv.adapter = adapter
    }

    private val rocketClickListener = object : RocketsAdapter.RocketClickListener {
        override fun onRocketClick(position: Int) {
            val intent = Intent(requireContext(), FragmentLoaderActivity::class.java)
            intent.putExtra(
                AppConstants.Defaults.FROM,
                AppConstants.FragmentConstants.ROCKET_DETAILS
            )
            intent.putExtra(AppConstants.Defaults.POSITION, position)
            intent.putExtra(AppConstants.ModelConstants.ROCKET, rockets[position])
            startForResult.launch(intent)
            requireActivity().overridePendingTransition(
                R.anim.enter,
                R.anim.exit,
            )
        }

        override fun onFavoriteClick(position: Int) {
            rockets[position].isFavorite = !rockets[position].isFavorite
            adapter.notifyItemChanged(position)
            (requireActivity() as MainActivity).updateFavoriteRockets(rockets[position])
        }

    }

    val startForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                val intent = result.data
                val pos = intent?.getIntExtra(AppConstants.Defaults.POSITION, 0)!!
                rockets[pos].isFavorite =
                    intent.getBooleanExtra(AppConstants.Defaults.IS_FAV, false)
                adapter.notifyItemChanged(pos)
                if (intent.getBooleanExtra(AppConstants.Defaults.IS_CLICKED, false))
                    (requireActivity() as MainActivity).updateFavoriteRockets(rockets[pos])
            }
        }

}