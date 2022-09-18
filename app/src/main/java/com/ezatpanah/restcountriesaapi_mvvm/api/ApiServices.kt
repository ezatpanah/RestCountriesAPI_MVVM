package com.ezatpanah.restcountriesaapi_mvvm.api

import com.ezatpanah.restcountriesaapi_mvvm.response.CountriesListResponse
import com.ezatpanah.restcountriesaapi_mvvm.response.CountryDetailsResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiServices {
    @GET("all")
    suspend fun getAllCountries(): MutableList<CountriesListResponse>

    @GET("name/{name}")
    suspend fun getCountryDetail(@Path("name") name: String): MutableList<CountryDetailsResponse>
}