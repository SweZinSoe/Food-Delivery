package com.szs.myfood.api

import com.szs.myfood.model.getMenuByRestaurant.GetMenuByRestaurant
import com.szs.myfood.model.getRestaurantByTownship.GetRestaurantByTownship
import com.szs.myfood.model.township.Township
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiClient {

    private val apiInterface: ApiInterface

    companion object{
        const val BASE_URL = "http://food-delivery-shwe-sin-soe.khaingthinkyi.me/api/"
    }

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        apiInterface = retrofit.create(ApiInterface::class.java)
    }

    fun getTownship(): Call<Township>{
        return apiInterface.getTownshipInterface()
    }

    fun getRestaurantByTownship(name: String): Call<GetRestaurantByTownship>{
        return apiInterface.getRestaurantByTownshipInterface(name)
    }

    fun getMenuByRestaurant(id: String): Call<GetMenuByRestaurant>{
        return apiInterface.getMenuByRestaurantInterface(id)
    }

}