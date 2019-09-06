package nl.rijksmuseum.core.network.service

import io.reactivex.Single
import nl.rijksmuseum.core.network.response.MuseumCollectionResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MuseumApiService {
    @GET("collection")
    fun getMuseumCollection(
        @Query("p") page: Int = 0,
        @Query("ps") limit: Int = 10,
        @Query("imgonly") boolean: Boolean = true
    ): Single<MuseumCollectionResponse>
}