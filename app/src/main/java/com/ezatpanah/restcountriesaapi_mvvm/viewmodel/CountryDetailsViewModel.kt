package com.ezatpanah.restcountriesaapi_mvvm.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ezatpanah.restcountriesaapi_mvvm.utils.DataStatus
import com.ezatpanah.restcountriesaapi_mvvm.repository.ApiRepository
import com.ezatpanah.restcountriesaapi_mvvm.response.CountryDetailsResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CountryDetailsViewModel @Inject constructor(
    private val repository: ApiRepository
) : ViewModel() {

    val countryDetails = MutableLiveData<DataStatus<MutableList<CountryDetailsResponse>>>()

    fun getCountryDetail(name: String) = viewModelScope.launch {
        repository.getCountryDetail(name).let {
            countryDetails.value = DataStatus.success(it)
        }
    }

    fun getCountryDetailCurrencies(): String {
        val _countryDetails = countryDetails.value?.data
        val stringBuilder = StringBuilder()
        if (_countryDetails!![0].currencies.isNotEmpty())
            _countryDetails[0].currencies.keys.let {
                for ((i, key) in it.withIndex()) {
                    val currencyName = _countryDetails[0].currencies[key]?.name
                    val currencySymbol = _countryDetails[0].currencies[key]?.symbol
                    Log.d("getCountryDetail","${currencyName}${currencySymbol}")
                    stringBuilder.append(currencyName)
                    stringBuilder.append(" - ")
                    stringBuilder.append(currencySymbol)
                    if (it.size > 1 && i < it.size - 1)
                        stringBuilder.append(" , ")
                }
            }
        return stringBuilder.toString()
    }

    fun getCountryDetailLanguages(): String {
        val _countryDetails = countryDetails.value?.data
        val stringBuilder = StringBuilder()
        if (_countryDetails?.get(0)?.languages!!.isNotEmpty())
            _countryDetails[0].languages.keys.let {
                for ((i, key) in it.withIndex()) {
                    val language = _countryDetails.get(0).languages[key]
                    Log.d("getCountryDetail","$language")
                    stringBuilder.append(language)
                    if (it.size > 1 && i < it.size - 1)
                        stringBuilder.append(" , ")
                }
            }
        return stringBuilder.toString()
    }

}