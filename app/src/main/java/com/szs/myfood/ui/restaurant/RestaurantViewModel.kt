package com.szs.myfood.ui.restaurant

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.szs.myfood.api.ApiClient
import com.szs.myfood.model.getRestaurantByTownship.GetRestaurantByTownship
import com.szs.myfood.model.township.TownshipX
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RestaurantViewModel(application: Application, townshipName: String) : ViewModel(){

    var restaurantResult: MutableLiveData<GetRestaurantByTownship> = MutableLiveData()
    var loadError: MutableLiveData<Boolean> = MutableLiveData()
    var loading: MutableLiveData<Boolean> = MutableLiveData()
    var mtownshipName = townshipName

    fun getRestaurantResult(): LiveData<GetRestaurantByTownship> = restaurantResult
    fun getError(): LiveData<Boolean> = loadError
    fun getLoading(): LiveData<Boolean> = loading

    private val apiClient: ApiClient = ApiClient()

    fun loadRestaurant(){

        loading.value = true
        val call = apiClient.getRestaurantByTownship(mtownshipName)

        call.enqueue(object: Callback<GetRestaurantByTownship>{
            override fun onFailure(call: Call<GetRestaurantByTownship>, t: Throwable) {
                loadError.value = true
                loading.value = false
            }

            override fun onResponse(
                call: Call<GetRestaurantByTownship>,
                response: Response<GetRestaurantByTownship>
            ) {
                response?.isSuccessful.let {
                    loading.value = false
                    val restaurantList = GetRestaurantByTownship(response?.body()?.restaurants?: emptyList())
                    restaurantResult.value = restaurantList
                }
            }

        })
    }

}