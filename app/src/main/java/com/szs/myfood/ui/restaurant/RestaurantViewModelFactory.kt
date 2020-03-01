package com.szs.myfood.ui.restaurant

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class RestaurantViewModelFactory internal constructor(private val mapplication: Application, private val mTownshipName: String)
    : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RestaurantViewModel::class.java)){
            return RestaurantViewModel(mapplication, mTownshipName) as T
        }
        throw IllegalArgumentException("Cannot create Instance for this class")
    }

}