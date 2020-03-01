package com.szs.myfood.ui.menu

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.szs.myfood.api.ApiClient
import com.szs.myfood.model.getMenuByRestaurant.GetMenuByRestaurant
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MenuViewModel(application: Application, restaurantID: String) : ViewModel() {

    var menuResults: MutableLiveData<GetMenuByRestaurant> = MutableLiveData()
    var loadError: MutableLiveData<Boolean> = MutableLiveData()
    var loading: MutableLiveData<Boolean> = MutableLiveData()
    var mRestaurantID = restaurantID

    fun getMenuResult(): LiveData<GetMenuByRestaurant> = menuResults
    fun getError(): LiveData<Boolean> = loadError
    fun getLoading(): LiveData<Boolean> = loading

    private val apiClient: ApiClient = ApiClient()

    fun loadMenuResults() {

        loading.value = true
        val call = apiClient.getMenuByRestaurant(mRestaurantID)
        call.enqueue(object : Callback<GetMenuByRestaurant> {
            override fun onFailure(call: Call<GetMenuByRestaurant>, t: Throwable) {
                loadError.value = true
                loading.value = false
            }

            override fun onResponse(
                call: Call<GetMenuByRestaurant>,
                response: Response<GetMenuByRestaurant>
            ) {
                response?.isSuccessful.let {
                    loading.value = false
                    val resultList = GetMenuByRestaurant(response?.body()?.menus?: emptyList())
                    Log.d("MenuByRestaurant>>>>", resultList.toString())
                    menuResults.value = resultList
                }
            }

        })
    }


}