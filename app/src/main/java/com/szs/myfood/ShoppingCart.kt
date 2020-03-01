package com.szs.myfood

import android.content.Context
import android.widget.Toast
import com.szs.myfood.model.cartMenu.CartMenu
import io.paperdb.Paper

class ShoppingCart {
    companion object {

        fun addMenu(cartMenu: CartMenu) {
            val cart = ShoppingCart.getCart()
            val targetMenu = cart.singleOrNull { it.menu.id == cartMenu.menu.id }
            if (targetMenu == null) {
                cartMenu.quantity++
                cart.add(cartMenu)
            } else {
                targetMenu.quantity++
            }
            ShoppingCart.saveCart(cart)
        }

        fun getAmount(cardMenu: List<CartMenu>): Double {
            var totalAmount = 0.0
            getCart().forEach {
                totalAmount += it.menu.menu_price.toDouble() * it.quantity


            }
            return totalAmount
        }

        fun removeMenu(cartMenu: CartMenu, context: Context) {
            val cart = ShoppingCart.getCart()
            val targetMenu = cart.singleOrNull { it.menu.id == cartMenu.menu.id }
            if (targetMenu != null) {
                if (targetMenu.quantity > 0) {
                    Toast.makeText(context, "Great quantity", Toast.LENGTH_LONG).show()
                    targetMenu.quantity--
                } else {
                    cart.remove(targetMenu)
                }
            }
            ShoppingCart.saveCart(cart)
        }

        fun saveCart(cart: MutableList<CartMenu>) {
            Paper.book().write("cart", cart)
        }

        fun getCart(): MutableList<CartMenu> {
            return Paper.book().read("cart", mutableListOf())
        }

        fun getShoppingCartSize(): Int {
            var cartSize = 0
            ShoppingCart.getCart().forEach {
                cartSize += it.quantity
            }
            return cartSize
        }

        fun deleteShoppingCart() {
            Paper.book().delete("cart")
        }
    }
}