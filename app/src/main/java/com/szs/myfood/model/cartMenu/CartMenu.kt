package com.szs.myfood.model.cartMenu

import com.szs.myfood.model.getMenuByRestaurant.Menu

data class CartMenu(var menu: Menu, var quantity: Int = 0 )