package nl.rijksmuseum.core.network.service

import io.reactivex.Single
import nl.rijksmuseum.core.network.response.MuseumCollectionResponse
import retrofit2.http.GET

interface MuseumApiService {
    @GET("collection")
    fun getMuseumCollection() : Single<MuseumCollectionResponse>

}