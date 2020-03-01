package com.szs.myfood.ui.menu

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class MenuViewModelFactory internal constructor(private val mapplication: Application, private val restaurantID: String): ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MenuViewModel::class.java)){
            return MenuViewModel(mapplication, restaurantID) as T
        }
        throw IllegalArgumentException("Cannot create Instance for this class")
    }

}