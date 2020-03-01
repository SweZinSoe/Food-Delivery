package com.szs.myfood.model.getMenuByRestaurant

data class Menu(
    val category: Category,
    val created_at: String,
    val description: String,
    val destination: String,
    val id: String,
    val menu_name: String,
    val menu_photo: String,
    val menu_price: String,
    val updated_at: String,
    val user_detail: UserDetail
)