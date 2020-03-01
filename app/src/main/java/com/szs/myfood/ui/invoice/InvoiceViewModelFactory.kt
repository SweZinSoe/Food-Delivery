package com.szs.myfood.ui.invoice

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.szs.myfood.model.cartMenu.CartMenu

class InvoiceViewModelFactory internal constructor(private val application: Application, private val menuName: CartMenu)
    : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(InvoiceViewModel::class.java)){
            return InvoiceViewModel(application, menuName) as T
        }
        throw IllegalArgumentException("Cannot create Instance for this class")
    }
}