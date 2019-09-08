package nl.rijksmuseum.screens.museum.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import dagger.android.support.DaggerFragment
import nl.rijksmuseum.core.network.response.ErrorResponse
import nl.rijksmuseum.core.network.response.ErrorResponse.Type.*
import nl.rijksmuseum.databinding.FragmentMuseumDetailBinding
import nl.rijksmuseum.screens.museum.viewmodel.MuseumDetailViewModel
import nl.rijksmuseum.utils.Constants
import nl.rijksmuseum.utils.ext.detectNetworkHealth
import nl.rijksmuseum.utils.ext.observe
import nl.rijksmuseum.utils.ext.toast
import javax.inject.Inject

class MuseumDetailFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: MuseumDetailViewModel by viewModels { viewModelFactory }

    private val args: MuseumDetailFragmentArgs by navArgs()

    private lateinit var binding: FragmentMuseumDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMuseumDetailBinding.inflate(inflater, container, false)
            .apply {
                lifecycleOwner = viewLifecycleOwner
                viewModel = this@MuseumDetailFragment.viewModel
                errorLayout.retryButton.setOnClickListener {
                    val isNetworkConnected = requireActivity().detectNetworkHealth()
                    if (isNetworkConnected) loadData()
                }
            }
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        loadData()
        subscribeUI()
    }

    private fun loadData() {
        viewModel.fetchMuseumArtDetail(args.museumId)
        binding.apply {
            errorLayout.errorView.visibility = View.GONE
            container.visibility = View.VISIBLE
        }
    }

    private fun subscribeUI() {
        observe(viewModel.getLoading()) { isLoading -> if (isLoading) startShimmer() else stopShimmer() }
        observe(viewModel.getErrorResponse()) { error -> onErrorResponse(error) }
    }

    private fun onErrorResponse(error: ErrorResponse) {
        binding.apply {
            container.visibility = View.GONE
            errorLayout.message.text = error.message ?: "Internal server error."
            errorLayout.errorView.visibility = View.VISIBLE
        }
        when (error.type) {
            GENERAL, HOTSPOT_LOGIN -> requireActivity().toast(error.message ?: "Internal server error.")
            else -> Constants.log("Error type : ${error.type}")
        }
    }

    private fun startShimmer() {
        binding.apply {
            shimmerView.apply {
                startShimmer()
                visibility = View.VISIBLE
            }
            scrollView.visibility = View.GONE
        }
    }

    private fun stopShimmer() {
        binding.apply {
            shimmerView.apply {
                stopShimmer()
                visibility = View.GONE
            }
            scrollView.visibility = View.VISIBLE
        }
    }
}