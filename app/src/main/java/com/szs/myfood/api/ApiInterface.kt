package com.szs.myfood.api

import com.szs.myfood.model.getMenuByRestaurant.GetMenuByRestaurant
import com.szs.myfood.model.getRestaurantByTownship.GetRestaurantByTownship
import com.szs.myfood.model.township.Township
import com.szs.myfood.model.township.TownshipX
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    @GET("setup/township")
    fun getTownshipInterface(): Call<Township>

    @GET("setup/restaurant_by_township")
    fun getRestaurantByTownshipInterface( @Query("township") township: String): Call<GetRestaurantByTownship>

    @GET("setup/menus_by_restaurant")
    fun getMenuByRestaurantInterface( @Query("restaurant_id") restaurant_id: String): Call<GetMenuByRestaurant>


}