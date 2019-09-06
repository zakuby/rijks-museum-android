package nl.rijksmuseum.models

data class Museum(
    val id: String? = "",
    val title: String? = "",
    val longTitle: String? = "",
    val principalOrFirstMaker: String? = "",
    val links: Links? = Links(),
    val webImage: WebImage? = WebImage(),
    val headerImage: HeaderImage? = HeaderImage()

) {
    data class Links(val web: String? = "")
    data class WebImage(val url: String? = "")
    data class HeaderImage(val url: String? = "")
}