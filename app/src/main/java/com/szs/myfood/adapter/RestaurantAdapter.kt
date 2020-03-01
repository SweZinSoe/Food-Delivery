package com.szs.myfood.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.szs.myfood.R
import com.szs.myfood.model.getRestaurantByTownship.Restaurant
import kotlinx.android.synthetic.main.restaurant.view.*

class RestaurantAdapter (var restaurantList: List<Restaurant> = ArrayList())
    : RecyclerView.Adapter<RestaurantAdapter.RestaurantViewHolder>(){

    var mClickListener: ClickListener? = null

    fun setOnClickListener(clickListener: ClickListener){
        this.mClickListener = clickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.restaurant, parent, false)
        return RestaurantViewHolder(view)
    }

    override fun getItemCount(): Int = restaurantList.size

    override fun onBindViewHolder(holder: RestaurantViewHolder, position: Int) {
        holder.bindRestaurant(restaurantList.get(position))
    }

    fun updateRestaurantList(restaurant: List<Restaurant>){
        restaurantList = restaurant
        notifyDataSetChanged()
    }

    inner class RestaurantViewHolder(itemView: View): RecyclerView.ViewHolder(itemView), View.OnClickListener{

        private var view: View = itemView
        private lateinit var restaurant: Restaurant

        init {
            itemView.setOnClickListener(this)
        }

        fun bindRestaurant(restaurant: Restaurant){
            this.restaurant = restaurant
            Picasso.get()
                .load(restaurant.photo)
                .placeholder(R.drawable.spicy)
                .into(view.restaurantImage)
            view.restaurantName.text = restaurant.user.name
            view.restaurantAddress.text = restaurant.address
            view.restaurantPhNo.text = restaurant.phone_no
        }

        override fun onClick(v: View?) {
            mClickListener?.onClick(restaurant)
        }

    }

    interface ClickListener{
        fun onClick(restaurant: Restaurant)
    }

}