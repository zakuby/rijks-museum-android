package nl.rijksmuseum.core.network.response

import com.google.gson.annotations.SerializedName
import nl.rijksmuseum.models.Museum

data class MuseumCollectionResponse(
    @SerializedName("artObjects") val museums: List<Museum>?
)