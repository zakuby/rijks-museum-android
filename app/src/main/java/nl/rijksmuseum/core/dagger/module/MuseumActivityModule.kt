package nl.rijksmuseum.core.dagger.module

import dagger.Module
import dagger.android.ContributesAndroidInjector
import nl.rijksmuseum.core.dagger.FragmentScoped
import nl.rijksmuseum.screens.museum.view.MuseumDetailFragment
import nl.rijksmuseum.screens.museum.view.MuseumListFragment
import nl.rijksmuseum.screens.profile.ProfileActivity

@Module
abstract class MuseumActivityModule {
    @FragmentScoped
    @ContributesAndroidInjector
    abstract fun contributeMuseumListFragment(): MuseumListFragment

    @FragmentScoped
    @ContributesAndroidInjector
    abstract fun contributeMuseumDetailFragment(): MuseumDetailFragment
}