package nl.rijksmuseum.core.network.response

import com.google.gson.annotations.SerializedName
import nl.rijksmuseum.models.MuseumArt
import nl.rijksmuseum.models.MuseumArtDetail

data class MuseumCollectionResponse(
    @SerializedName("artObjects") val museumArts: List<MuseumArt>?
)

data class MuseumCollectionDetailResponse(
    @SerializedName("artObject") val museumArt: MuseumArtDetail?
)