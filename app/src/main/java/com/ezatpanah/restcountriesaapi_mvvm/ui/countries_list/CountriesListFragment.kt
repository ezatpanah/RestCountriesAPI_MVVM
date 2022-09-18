package com.ezatpanah.restcountriesaapi_mvvm.ui.countries_list

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ezatpanah.restcountriesaapi_mvvm.adapter.CountriesListAdapter
import com.ezatpanah.restcountriesaapi_mvvm.adapter.visible
import com.ezatpanah.restcountriesaapi_mvvm.api.DataStatus
import com.ezatpanah.restcountriesaapi_mvvm.databinding.FragmentCountriesListBinding
import com.ezatpanah.restcountriesaapi_mvvm.utils.CheckConnection
import com.ezatpanah.restcountriesaapi_mvvm.viewmodel.CountriesListViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class CountriesListFragment : Fragment() {

    lateinit var binding: FragmentCountriesListBinding
    private val checkConnection by lazy { CheckConnection(requireActivity().application) }
    private val countriesListviewModel: CountriesListViewModel by viewModels()
    var doubleBackToExitPressedOnce = false

    @Inject
    lateinit var countriesListAdapter: CountriesListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentCountriesListBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            checkConnection.observe(viewLifecycleOwner) {
                if (it) {
                    homeContent.visible(true)
                    noInternet.visible(false)
                    progressBar.visible(true)
                    countriesListviewModel.getAllCountries()
                    countriesListviewModel.countries.observe(viewLifecycleOwner) { itData ->
                        when (itData.status) {
                            DataStatus.Status.LOADING -> {
                                progressBar.visible(true)
                            }
                            DataStatus.Status.SUCCESS -> {
                                progressBar.visible(false)
                                countriesListAdapter.setItems(itData.data)
                                rvCountries.apply {
                                    layoutManager = LinearLayoutManager(requireContext())
                                    adapter = countriesListAdapter
                                }
                                countriesListAdapter.setOnItemClickListener { countryInfo ->
                                    val direction = CountriesListFragmentDirections.actionCountriesListFragmentToCountryDetailFragment(countryInfo.name.common)
                                    findNavController().navigate(direction)
                                }
                            }
                            DataStatus.Status.ERROR -> {
                                Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                } else {
                    homeContent.visible(false)
                    noInternet.visible(true)
                }
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (doubleBackToExitPressedOnce) {
                    activity?.finish()
                }
                doubleBackToExitPressedOnce = true
                Handler().postDelayed({ doubleBackToExitPressedOnce = false }, 2000)
                Toast.makeText(requireContext(), "Double press to exit", Toast.LENGTH_SHORT).show()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

}