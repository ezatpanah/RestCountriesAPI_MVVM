package com.ezatpanah.restcountriesaapi_mvvm.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ezatpanah.restcountriesaapi_mvvm.databinding.ItemCountryBinding
import com.ezatpanah.restcountriesaapi_mvvm.response.CountriesListResponse
import javax.inject.Inject


class CountriesListAdapter @Inject constructor() : RecyclerView.Adapter<CountriesListAdapter.ViewHolder>() {

    lateinit var binding: ItemCountryBinding
    private val countriesList = ArrayList<CountriesListResponse>()

    @SuppressLint("NotifyDataSetChanged")
    fun setItems(items: List<CountriesListResponse>?) {
        this.countriesList.clear()
        items?.let {
            this.countriesList.addAll(it)
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        binding = ItemCountryBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(countriesList[position])
    }

    override fun getItemCount(): Int = countriesList.size

    override fun getItemViewType(position: Int): Int = position

    inner class ViewHolder(val view: ItemCountryBinding) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(item: CountriesListResponse) {
            view.countriesListResponse = item
            view.root.setOnClickListener {
                onItemClickListener?.let {
                    it(item)
                }
            }
            view.executePendingBindings()
        }
    }

    private var onItemClickListener: ((CountriesListResponse) -> Unit)? = null

    fun setOnItemClickListener(listener: (CountriesListResponse) -> Unit) {
        onItemClickListener = listener
    }

}


