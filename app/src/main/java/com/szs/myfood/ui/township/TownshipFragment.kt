package com.szs.myfood.ui.township

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import com.szs.myfood.R
import com.szs.myfood.model.township.TownshipX
import com.szs.myfood.ui.restaurant.RestaurantFragment
import com.szs.myfood.ui.restaurant.RestaurantFragmentDirections
import com.szs.myfood.ui.restaurant.RestaurantViewModel
import kotlinx.android.synthetic.main.fragment_township.view.*

class TownshipFragment : Fragment() {

    private lateinit var townshipViewModel: TownshipViewModel
    private var townshipArray: List<TownshipX> = ArrayList()
    lateinit var spinner: Spinner
    lateinit var selectedTownship: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_township, container, false)

        spinner = view.findViewById(R.id.spinner)

        view.btn_TownshipContinue.setOnClickListener {

            selectedTownship = spinner.selectedItem.toString()

            val action =
                TownshipFragmentDirections.actionNavHomeToRestaurantFragment(selectedTownship)

            view.findNavController().navigate(action)
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
    }

    fun observeViewModel() {
        townshipViewModel = ViewModelProvider(this).get(TownshipViewModel::class.java)
        townshipViewModel.loadTownship()
        townshipViewModel.getTownship().observe(
            viewLifecycleOwner, Observer { result ->
                townshipArray = result.townships

                var data: MutableList<String?> = ArrayList()

                townshipArray?.forEach {
                    data.add(0, it.township_name)
                }
                spinner.adapter = context?.let {
                    ArrayAdapter<String>(
                        it,
                        R.layout.support_simple_spinner_dropdown_item,
                        data
                    )
                }
            }
        )
    }
}

