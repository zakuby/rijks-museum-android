package nl.rijksmuseum.screens.museum.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.android.support.DaggerFragment
import nl.rijksmuseum.core.network.response.ErrorResponse
import nl.rijksmuseum.core.network.response.ErrorResponse.Type.GENERAL
import nl.rijksmuseum.core.network.response.ErrorResponse.Type.HOTSPOT_LOGIN
import nl.rijksmuseum.databinding.FragmentMuseumListBinding
import nl.rijksmuseum.screens.museum.adapter.MuseumListAdapter
import nl.rijksmuseum.screens.museum.viewmodel.MuseumListViewModel
import nl.rijksmuseum.utils.Constants
import nl.rijksmuseum.utils.ext.observe
import nl.rijksmuseum.utils.ext.toast
import javax.inject.Inject

class MuseumListFragment : DaggerFragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: MuseumListViewModel by viewModels { viewModelFactory }

    private val adapter: MuseumListAdapter by lazy {
        MuseumListAdapter { id -> navigateToDetail(id) }
    }

    private lateinit var binding: FragmentMuseumListBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMuseumListBinding.inflate(inflater, container, false)
            .apply {
                lifecycleOwner = viewLifecycleOwner
                viewModel = this@MuseumListFragment.viewModel
                errorLayout.retryButton.setOnClickListener { loadData() }
                recyclerView.apply {
                    layoutManager = LinearLayoutManager(requireActivity())
                    adapter = this@MuseumListFragment.adapter
                }
            }
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        loadData()
        subscribeUI()
    }

    private fun subscribeUI() {
        observe(viewModel.getMuseumCollections()) { museumArts ->
            adapter.loadMuseumCollections(museumArts)
        }
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

    private fun loadData() {
        binding.apply {
            errorLayout.errorView.visibility = View.GONE
            container.visibility = View.VISIBLE
        }
        viewModel.fetchMuseumCollections()
    }

    private fun startShimmer() {
        binding.apply {
            shimmerView.apply {
                startShimmer()
                visibility = View.VISIBLE
            }
            recyclerView.visibility = View.GONE
        }
    }

    private fun stopShimmer() {
        binding.apply {
            shimmerView.apply {
                stopShimmer()
                visibility = View.GONE
            }
            recyclerView.visibility = View.VISIBLE
        }
    }

    private fun navigateToDetail(museumId: String) {
        val direction =
            MuseumListFragmentDirections.actionMuseumListFragmentToMuseumDetailFragment(museumId)
        findNavController().navigate(direction)
    }
}