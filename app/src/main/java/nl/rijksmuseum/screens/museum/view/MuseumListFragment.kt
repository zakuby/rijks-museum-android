package nl.rijksmuseum.screens.museum.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import dagger.android.support.DaggerFragment
import nl.rijksmuseum.databinding.FragmentMuseumListBinding
import nl.rijksmuseum.screens.museum.viewmodel.MuseumListViewModel
import nl.rijksmuseum.utils.Constants
import nl.rijksmuseum.utils.ext.observe
import javax.inject.Inject

class MuseumListFragment : DaggerFragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: MuseumListViewModel by viewModels { viewModelFactory }

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
            }
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        observe(viewModel.getMuseumCollections()) { museums ->
            //TODO("Load museum data to recyclerView Adapter")
            Constants.log("$museums")
        }
    }
}