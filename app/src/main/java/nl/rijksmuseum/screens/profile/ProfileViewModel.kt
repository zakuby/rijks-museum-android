package nl.rijksmuseum.screens.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import nl.rijksmuseum.core.base.BaseViewModel
import nl.rijksmuseum.models.Profile
import javax.inject.Inject

class ProfileViewModel @Inject constructor() : BaseViewModel() {

    private val profile = MutableLiveData<Profile>()

    init { profile.postValue(Profile()) }


    fun getProfile(): LiveData<Profile> = profile
}