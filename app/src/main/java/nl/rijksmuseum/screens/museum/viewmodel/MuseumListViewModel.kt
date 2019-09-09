package nl.rijksmuseum.screens.museum.viewmodel

import androidx.databinding.ObservableBoolean
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

    val isResponseError = ObservableBoolean(false)

    fun getErrorResponse(): LiveData<ErrorResponse> = errorResponse

    fun getMuseumCollections(): LiveData<List<MuseumArt>> = museumCollections

    init {
        fetchMuseumCollections()
    }

    fun fetchMuseumCollections() {
        service.getMuseumCollection()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { setLoading() }
            .doAfterTerminate { finishLoading() }
            .subscribeBy(
                onSuccess = { resp ->
                    isResponseError.set(false)
                    resp.museumArts?.let { museums ->
                        museumCollections.postValue(museums)
                    } ?: Constants.log("MuseumArt list empty")
                },
                onError = { error ->
                    isResponseError.set(true)
                    errorResponse.postValue(errorResponseHandler.handleException(error))
                    Constants.log("Error fetching museum collections : $error")
                }
            )
            .disposedBy(compositeDisposable)
    }
}