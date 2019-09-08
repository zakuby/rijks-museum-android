package nl.rijksmuseum.screens.museum.viewmodel

import nl.rijksmuseum.core.base.BaseViewModel
import nl.rijksmuseum.core.network.service.MuseumApiService
import javax.inject.Inject

class MuseumDetailViewModel @Inject constructor(
    private val service: MuseumApiService
) : BaseViewModel()