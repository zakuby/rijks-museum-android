package nl.rijksmuseum.models

import com.google.gson.annotations.SerializedName

data class MuseumArtDetail(
    @SerializedName("objectNumber") val id: String? = "",
    val longTitle: String? = "",
    val physicalMedium: String? = "",
    val subTitle: String? = "",
    val description: String? = "",
    val principalOrFirstMaker: String? = "",
    val label: Label? = Label(),
    val webImage: MuseumArt.WebImage? = MuseumArt.WebImage()
) {
    data class Label(
        val description: String? = "",
        val date: String? = ""
    )

    fun getArtDate(): String = "Date : ${label?.date}"

    fun getArtSubtitle(): String = "$physicalMedium, $subTitle"

    fun getArtDescription(): String = label?.description ?: description ?: ""
}