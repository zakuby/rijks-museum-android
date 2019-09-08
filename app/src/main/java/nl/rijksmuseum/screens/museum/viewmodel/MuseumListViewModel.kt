package nl.rijksmuseum.screens.museum.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import nl.rijksmuseum.core.base.BaseViewModel
import nl.rijksmuseum.core.network.service.MuseumApiService
import nl.rijksmuseum.models.Museum
import nl.rijksmuseum.utils.Constants
import nl.rijksmuseum.utils.ext.disposedBy
import javax.inject.Inject

class MuseumListViewModel @Inject constructor(
    private val service: MuseumApiService
) : BaseViewModel() {

    private val museumCollections = MutableLiveData<List<Museum>>()

    fun getMuseumCollections(): LiveData<List<Museum>> = museumCollections

    fun fetchMuseumCollections() {
        service.getMuseumCollection()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { setLoading() }
            .doAfterTerminate { finishLoading() }
            .subscribeBy(
                onSuccess = { resp ->
                    resp.museums?.let { museums ->
                        museumCollections.postValue(museums)
                    } ?: Constants.log("Museum list empty")
                },
                onError = { error ->
                    Constants.log("Error fetching museum collections : $error")
                }
            )
            .disposedBy(compositeDisposable)
    }
}