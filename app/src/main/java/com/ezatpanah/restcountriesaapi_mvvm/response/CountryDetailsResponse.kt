package com.ezatpanah.restcountriesaapi_mvvm.response


import com.google.gson.annotations.SerializedName

data class CountryDetailsResponse(
    val capital: List<String>,
    @SerializedName("currencies")
    val currencies: HashMap<String, CurrencyDetail> = HashMap(),
    @SerializedName("flags")
    val flags: Flags,
    @SerializedName("languages")
    val languages: HashMap<String,String> = HashMap(),
    @SerializedName("maps")
    val maps: Maps,
    @SerializedName("name")
    val name: Name,
    @SerializedName("population")
    val population: Long,
    @SerializedName("region")
    val region: String,
    @SerializedName("subregion")
    val subregion: String

) {


    data class Flags(
        @SerializedName("png")
        val png: String,
        @SerializedName("svg")
        val svg: String
    )


    data class Maps(
        @SerializedName("googleMaps")
        val googleMaps: String,
        @SerializedName("openStreetMaps")
        val openStreetMaps: String
    )

    data class Name(
        @SerializedName("common")
        val common: String,
        @SerializedName("official")
        val official: String
    )

    class CurrencyDetail(
        val name: String,
        val symbol: String
    )

}