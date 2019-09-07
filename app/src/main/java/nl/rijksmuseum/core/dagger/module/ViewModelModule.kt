package nl.rijksmuseum.core.dagger.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import nl.rijksmuseum.core.dagger.ViewModelKey
import nl.rijksmuseum.screens.museum.viewmodel.MuseumDetailViewModel
import nl.rijksmuseum.screens.museum.viewmodel.MuseumListViewModel
import nl.rijksmuseum.screens.profile.ProfileViewModel
import nl.rijksmuseum.utils.ViewModelFactory

@Module
abstract class ViewModelModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(MuseumListViewModel::class)
    internal abstract fun museumListViewModel(viewModel: MuseumListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MuseumDetailViewModel::class)
    internal abstract fun museumDetailViewModel(viewModel: MuseumDetailViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ProfileViewModel::class)
    internal abstract fun profileViewModel(viewModel: ProfileViewModel): ViewModel
}