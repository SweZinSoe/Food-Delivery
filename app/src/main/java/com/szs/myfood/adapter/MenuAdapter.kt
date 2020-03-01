package com.szs.myfood.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import com.szs.myfood.MainActivity
import com.szs.myfood.R
import com.szs.myfood.ShoppingCart
import com.szs.myfood.model.cartMenu.CartMenu
import com.szs.myfood.model.getMenuByRestaurant.Menu
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import kotlinx.android.synthetic.main.menu.*
import kotlinx.android.synthetic.main.menu.view.*

class MenuAdapter(var menuList: List<Menu> = ArrayList()) :
    RecyclerView.Adapter<MenuAdapter.MenuViewHolder>() {
    var count = 0
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.menu, parent, false)
        return MenuViewHolder(view)
    }

    override fun getItemCount(): Int = menuList.size

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        holder.bindMenu(menuList.get(position))
    }

    fun updateMenuList(menu: List<Menu>) {
        menuList = menu
        notifyDataSetChanged()
    }

    inner class MenuViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private lateinit var menuArrayList: MutableList<String>
        private var view: View = itemView
        private lateinit var menu: Menu

        @SuppressLint("CheckResult")
        fun bindMenu(menu: Menu) {
            this.menu = menu
            Picasso.get()
                .load(menu.menu_photo)
                .placeholder(R.drawable.spicy)
                .into(view.menuImage)
            view.menuName.text = menu.menu_name
            view.menuPrice.text = menu.menu_price

            Observable.create(ObservableOnSubscribe<MutableList<CartMenu>> {

                view.btn_increase.setOnClickListener { v ->

                    count += 1
                    view.menuCount.text = count.toString()
                    Log.d("Count>>>>", count.toString())

                    val menuItem = CartMenu(menu)
                    ShoppingCart.addMenu(menuItem)
                    Snackbar.make(
                        (view.context as MainActivity).menu_layout,
                        "${menu.menu_name} added to your cart",
                        Snackbar.LENGTH_LONG
                    ).show()

                    it.onNext(ShoppingCart.getCart())
                }

                view.btn_decrease.setOnClickListener { v ->

                    count--
                    if (count > 0)
                        view.menuCount.text = count.toString()
                    else
                        view.menuCount.text = "0"

                    val menuItem = CartMenu(menu)
                    ShoppingCart.removeMenu(menuItem, view.context)
                    Snackbar.make(
                        (view.context as MainActivity).menu_layout,
                        "${menu.menu_name} removed from your cart",
                        Snackbar.LENGTH_LONG
                    ).show()

                    it.onNext(ShoppingCart.getCart())
                }

            }).subscribe { cart ->
                var qty = 0
                cart.forEach { cartMenu ->
                    qty += cartMenu.quantity
                }
                Toast.makeText(view.context, "Cart size ${ShoppingCart.getShoppingCartSize()}", Toast.LENGTH_LONG).show()
            }

            view.btn_addToCart.setOnClickListener { v ->
                Snackbar.make(
                    (view.context as MainActivity).menu_layout,
                    "Check and See your Cart!",
                    Snackbar.LENGTH_LONG
                ).show()
            }

        }
    }
}

