package com.ezatpanah.restcountriesaapi_mvvm.ui.country_detailes

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.ezatpanah.restcountriesaapi_mvvm.adapter.visible
import com.ezatpanah.restcountriesaapi_mvvm.utils.DataStatus
import com.ezatpanah.restcountriesaapi_mvvm.databinding.FragmentCountryDetailBinding
import com.ezatpanah.restcountriesaapi_mvvm.utils.CheckConnection
import com.ezatpanah.restcountriesaapi_mvvm.viewmodel.CountryDetailsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CountryDetailFragment : Fragment() {

    lateinit var binding: FragmentCountryDetailBinding
    private val checkConnection by lazy { CheckConnection(requireActivity().application) }
    private val args: CountryDetailFragmentArgs by navArgs()
    private val viewModel: CountryDetailsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCountryDetailBinding.inflate(inflater, container, false)
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
                    viewModel.getCountryDetail(args.countryName)
                    viewModel.countryDetails.observe(viewLifecycleOwner) { itData ->
                        when (itData.status) {
                            DataStatus.Status.LOADING -> {
                                progressBar.visible(true)
                            }
                            DataStatus.Status.SUCCESS -> {
                                progressBar.visible(false)
                                binding.countryDetailsResponse = itData.data?.get(0)
                                binding.countryDetailsViewModel = viewModel
                                btnMap.setOnClickListener {
                                    val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(itData.data?.get(0)?.maps?.googleMaps))
                                    requireContext().startActivity(browserIntent)
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
}