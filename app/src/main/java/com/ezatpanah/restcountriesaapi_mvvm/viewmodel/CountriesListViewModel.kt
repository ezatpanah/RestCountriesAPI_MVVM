package com.ezatpanah.restcountriesaapi_mvvm.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ezatpanah.restcountriesaapi_mvvm.utils.DataStatus
import com.ezatpanah.restcountriesaapi_mvvm.repository.ApiRepository
import com.ezatpanah.restcountriesaapi_mvvm.response.CountriesListResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CountriesListViewModel
@Inject constructor(
    private val repository: ApiRepository
    ) : ViewModel() {

    val countries = MutableLiveData<DataStatus<MutableList<CountriesListResponse>>>()

    fun getAllCountries() = viewModelScope.launch {
        repository.getAllCountries().let {
            countries.value = DataStatus.success(it)
        }
    }

}


