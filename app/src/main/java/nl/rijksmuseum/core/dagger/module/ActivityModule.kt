package nl.rijksmuseum.core.dagger.module

import dagger.Module
import dagger.android.ContributesAndroidInjector
import nl.rijksmuseum.core.dagger.ActivityScoped
import nl.rijksmuseum.screens.WebViewActivity
import nl.rijksmuseum.screens.museum.view.MuseumActivity
import nl.rijksmuseum.screens.profile.ProfileFragment

@Module
abstract class ActivityModule {
    @ActivityScoped
    @ContributesAndroidInjector(modules = [MuseumActivityModule::class])
    abstract fun contributeMainActivity(): MuseumActivity

    @ActivityScoped
    @ContributesAndroidInjector
    abstract fun contributeWebViewActivity(): WebViewActivity
}