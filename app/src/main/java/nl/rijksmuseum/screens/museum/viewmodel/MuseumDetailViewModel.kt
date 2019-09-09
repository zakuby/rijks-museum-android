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
import nl.rijksmuseum.models.MuseumArtDetail
import nl.rijksmuseum.utils.Constants
import nl.rijksmuseum.utils.ext.disposedBy
import javax.inject.Inject

class MuseumDetailViewModel @Inject constructor(
    private val errorResponseHandler: ErrorResponseHandler,
    private val service: MuseumApiService
) : BaseViewModel() {

    private val museumArt = MutableLiveData<MuseumArtDetail>()

    private val errorResponse = MutableLiveData<ErrorResponse>()

    val isResponseError = ObservableBoolean(false)

    fun getErrorResponse(): LiveData<ErrorResponse> = errorResponse

    fun getMuseumArt(): LiveData<MuseumArtDetail> = museumArt

    fun fetchMuseumArtDetail(id: String) {
        service.getMuseumCollectionDetail(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { setLoading() }
            .doAfterTerminate { finishLoading() }
            .subscribeBy(
                onSuccess = { resp ->
                    isResponseError.set(false)
                    resp.museumArt?.let { museumArtDetail ->
                        museumArt.postValue(museumArtDetail)
                    } ?: Constants.log("Error fetching museum art detail, data is null")
                },
                onError = { error ->
                    isResponseError.set(true)
                    errorResponse.postValue(errorResponseHandler.handleException(error))
                    Constants.log("Error fetching museum art detail : $error")
                }
            ).disposedBy(compositeDisposable)
    }
}