package nl.rijksmuseum.screens.museum.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.android.support.DaggerFragment
import nl.rijksmuseum.databinding.FragmentMuseumListBinding
import nl.rijksmuseum.screens.museum.adapter.MuseumListAdapter
import nl.rijksmuseum.screens.museum.viewmodel.MuseumListViewModel
import nl.rijksmuseum.utils.ext.observe
import javax.inject.Inject

class MuseumListFragment : DaggerFragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: MuseumListViewModel by viewModels { viewModelFactory }

    private val adapter: MuseumListAdapter by lazy { MuseumListAdapter() }

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
                recyclerView.apply {
                    layoutManager = LinearLayoutManager(requireActivity())
                    adapter = this@MuseumListFragment.adapter
                }
            }
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.fetchMuseumCollections()
        subscribeUI()
    }

    private fun subscribeUI() {
        observe(viewModel.getMuseumCollections()) { museums ->
            adapter.loadMuseumCollections(museums)
        }
        observe(viewModel.getLoading()) { isLoading ->
            if (isLoading) startShimmer() else stopShimmer()
        }
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
}