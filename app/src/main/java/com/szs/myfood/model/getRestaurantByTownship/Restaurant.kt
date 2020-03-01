package com.szs.myfood.model.getRestaurantByTownship

data class Restaurant(
    val address: String,
    val created_at: String,
    val date_of_birth: Any,
    val gender: Any,
    val id: String,
    val nrc_no: Any,
    val phone_no: String,
    val photo: String,
    val role: String,
    val township: Township,
    val updated_at: String,
    val user: User
)