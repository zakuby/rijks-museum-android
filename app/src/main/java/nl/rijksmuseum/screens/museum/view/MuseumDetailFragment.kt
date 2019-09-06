package nl.rijksmuseum.screens.museum.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import dagger.android.support.DaggerFragment
import nl.rijksmuseum.databinding.FragmentMuseumDetailBinding
import nl.rijksmuseum.screens.museum.viewmodel.MuseumDetailViewModel
import javax.inject.Inject

class MuseumDetailFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: MuseumDetailViewModel by viewModels { viewModelFactory }

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
            }
        return binding.root
    }
}