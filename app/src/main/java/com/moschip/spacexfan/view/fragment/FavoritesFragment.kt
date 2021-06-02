package com.moschip.spacexfan.view.fragment

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.moschip.services.model.Rocket
import com.moschip.spacexfan.R
import com.moschip.spacexfan.adapter.RocketsAdapter
import com.moschip.spacexfan.databinding.FragmentFavoritesBinding
import com.moschip.spacexfan.helper.AppConstants
import com.moschip.spacexfan.view.activity.FragmentLoaderActivity
import com.moschip.spacexfan.view.activity.MainActivity
import com.moschip.spacexfan.viewmodel.fragment.FavoritesViewModel
import java.util.concurrent.Executor


class FavoritesFragment : Fragment() {

    private var favoriteRockets: List<Rocket> = arrayListOf()
    private lateinit var adapter: RocketsAdapter
    private lateinit var binding: FragmentFavoritesBinding
    private var clickedPos = 0

    private lateinit var executor: Executor
    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var promptInfo: BiometricPrompt.PromptInfo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            favoriteRockets = it.getParcelableArrayList(AppConstants.ModelConstants.FAVORITE)!!
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoritesBinding.inflate(inflater, container, false).apply {
            viewModel = ViewModelProvider(requireActivity()).get(FavoritesViewModel::class.java)
            lifecycleOwner = viewLifecycleOwner
        }
        binding.viewModel?.binding = binding
        return binding.root
    }

    companion object {

        @JvmStatic
        fun newInstance(favoriteRockets: ArrayList<Rocket>) =
            FavoritesFragment().apply {
                arguments = Bundle().apply {
                    putParcelableArrayList(AppConstants.ModelConstants.FAVORITE, favoriteRockets)
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (requireActivity() as MainActivity).setNavigationItemSelected(R.id.favorite)

        bindRecyclerView()

        executor = ContextCompat.getMainExecutor(requireContext())
        biometricPrompt = BiometricPrompt(this, executor,
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationError(
                    errorCode: Int,
                    errString: CharSequence
                ) {
                    super.onAuthenticationError(errorCode, errString)
                    Toast.makeText(
                        requireContext(),
                        "$errString", Toast.LENGTH_SHORT
                    )
                        .show()
                }

                override fun onAuthenticationSucceeded(
                    result: BiometricPrompt.AuthenticationResult
                ) {
                    super.onAuthenticationSucceeded(result)
                    val intent = Intent(requireContext(), FragmentLoaderActivity::class.java)
                    intent.putExtra(
                        AppConstants.Defaults.FROM,
                        AppConstants.FragmentConstants.ROCKET_DETAILS
                    )
                    intent.putExtra(AppConstants.Defaults.POSITION, clickedPos)
                    intent.putExtra(AppConstants.ModelConstants.ROCKET, favoriteRockets[clickedPos])
                    startForResult.launch(intent)
                    requireActivity().overridePendingTransition(
                        R.anim.enter,
                        R.anim.exit,
                    )
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    Toast.makeText(
                        requireContext(), "Authentication failed",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
            })

        promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Biometric login for watching rocket details")
            .setSubtitle("Verify using your biometric credential")
            .setNegativeButtonText("cancel")
            .build()


    }

    private fun bindRecyclerView() {
        adapter = RocketsAdapter(favoriteRockets)
        adapter.setRocketClickListener(rocketClickListener)
        binding.favoriteRocketsRv.adapter = adapter
        binding.viewModel?.handleVisibility(favoriteRockets.size)
    }

    private val rocketClickListener = object : RocketsAdapter.RocketClickListener {
        override fun onRocketClick(position: Int) {
            clickedPos = position
            biometricPrompt.authenticate(promptInfo)
            /* val biometricManager = BiometricManager.from(requireContext())
             when (biometricManager.canAuthenticate(BIOMETRIC_STRONG or DEVICE_CREDENTIAL)) {
                 BiometricManager.BIOMETRIC_SUCCESS -> {
                     Log.d("MY_APP_TAG", "App can authenticate using biometrics.")
                     biometricPrompt.authenticate(promptInfo)
                 }
                 BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE ->
                     Log.e("MY_APP_TAG", "No biometric features available on this device.")
                 BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE ->
                     Log.e("MY_APP_TAG", "Biometric features are currently unavailable.")
                 BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> {
                     // Prompts the user to create credentials that your app accepts.
                     val enrollIntent = Intent(Settings.ACTION_BIOMETRIC_ENROLL).apply {
                         putExtra(
                             Settings.EXTRA_BIOMETRIC_AUTHENTICATORS_ALLOWED,
                             BIOMETRIC_STRONG or DEVICE_CREDENTIAL
                         )
                     }
                     startActivityForResult(enrollIntent, 1001)
                 }
             }*/
        }

        override fun onFavoriteClick(position: Int) {
            favoriteRockets[position].isFavorite = !favoriteRockets[position].isFavorite
            adapter.notifyDataSetChanged()
            (requireActivity() as MainActivity).updateFavoriteRockets(favoriteRockets[position])
            binding.viewModel?.handleVisibility(favoriteRockets.size)
        }
    }

    val startForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                val intent = result.data
                val pos = intent?.getIntExtra(AppConstants.Defaults.POSITION, 0)!!
                favoriteRockets[pos].isFavorite =
                    intent.getBooleanExtra(AppConstants.Defaults.IS_FAV, false)
                adapter.notifyDataSetChanged()
                if (intent.getBooleanExtra(AppConstants.Defaults.IS_CLICKED, false))
                    (requireActivity() as MainActivity).updateFavoriteRockets(favoriteRockets[pos])
                binding.viewModel?.handleVisibility(favoriteRockets.size)

            }
        }
}