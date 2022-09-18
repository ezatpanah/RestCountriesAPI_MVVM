package com.ezatpanah.restcountriesaapi_mvvm.response


import com.google.gson.annotations.SerializedName

data class CountriesListResponse(
    val id: Int,
    @SerializedName("capital")
    val capital: List<String>,
    @SerializedName("flags")
    val flags: Flags,
    @SerializedName("name")
    val name: Name,
    @SerializedName("region")
    val region: String
) {

    data class Flags(
        @SerializedName("png")
        val png: String
    )

    data class Name(
        @SerializedName("common")
        val common: String,
        @SerializedName("official")
        val official: String
    )
}
