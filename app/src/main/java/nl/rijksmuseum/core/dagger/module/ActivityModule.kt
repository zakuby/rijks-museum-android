package nl.rijksmuseum.core.dagger.module

import dagger.Module
import dagger.android.ContributesAndroidInjector
import nl.rijksmuseum.core.dagger.ActivityScoped
import nl.rijksmuseum.screens.museum.view.MuseumActivity

@Module
abstract class ActivityModule {
    @ActivityScoped
    @ContributesAndroidInjector
    abstract fun contributeMuseumActivity() : MuseumActivity
}