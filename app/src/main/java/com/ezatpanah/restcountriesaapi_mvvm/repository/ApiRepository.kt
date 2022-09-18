package com.ezatpanah.restcountriesaapi_mvvm.repository

import com.ezatpanah.restcountriesaapi_mvvm.api.ApiServices
import com.ezatpanah.restcountriesaapi_mvvm.response.CountriesListResponse
import com.ezatpanah.restcountriesaapi_mvvm.response.CountryDetailsResponse
import javax.inject.Inject

class ApiRepository
@Inject constructor(
    private val apiServices: ApiServices
) {
    suspend fun getAllCountries(): MutableList<CountriesListResponse> = apiServices.getAllCountries()
    suspend fun getCountryDetail(name: String): MutableList<CountryDetailsResponse> = apiServices.getCountryDetail(name)
}