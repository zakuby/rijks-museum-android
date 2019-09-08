package nl.rijksmuseum.screens.museum.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import nl.rijksmuseum.core.base.BaseViewModel
import nl.rijksmuseum.core.network.response.ErrorResponse
import nl.rijksmuseum.core.network.response.ErrorResponseHandler
import nl.rijksmuseum.core.network.service.MuseumApiService
import nl.rijksmuseum.models.MuseumArt
import nl.rijksmuseum.utils.Constants
import nl.rijksmuseum.utils.ext.disposedBy
import javax.inject.Inject

class MuseumListViewModel @Inject constructor(
    private val errorResponseHandler: ErrorResponseHandler,
    private val service: MuseumApiService
) : BaseViewModel() {

    private val museumCollections = MutableLiveData<List<MuseumArt>>()

    private val errorResponse = MutableLiveData<ErrorResponse>()

    fun getErrorResponse(): LiveData<ErrorResponse> = errorResponse

    fun getMuseumCollections(): LiveData<List<MuseumArt>> = museumCollections

    fun fetchMuseumCollections() {
        service.getMuseumCollection()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { setLoading() }
            .doAfterTerminate { finishLoading() }
            .subscribeBy(
                onSuccess = { resp ->
                    resp.museumArts?.let { museums ->
                        museumCollections.postValue(museums)
                    } ?: Constants.log("MuseumArt list empty")
                },
                onError = { error ->
                    errorResponse.postValue(errorResponseHandler.handleException(error))
                    Constants.log("Error fetching museum collections : $error")
                }
            )
            .disposedBy(compositeDisposable)
    }
}