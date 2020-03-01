package com.szs.myfood.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.szs.myfood.R
import com.szs.myfood.ShoppingCart
import com.szs.myfood.model.cartMenu.CartMenu
import kotlinx.android.synthetic.main.invoice_list.view.*

class InvoiceListAdapter(var context: Context, var cartMenu: List<CartMenu>)
    : RecyclerView.Adapter<InvoiceListAdapter.InvoiceListViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InvoiceListViewHolder {
        var view = LayoutInflater.from(context).inflate(R.layout.invoice_list, parent, false)
        return InvoiceListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return cartMenu.size
    }

    override fun onBindViewHolder(holder: InvoiceListViewHolder, position: Int) {
        holder.bindMenuInvoiceList(cartMenu[position])
    }

    class InvoiceListViewHolder(view: View): RecyclerView.ViewHolder(view){
        fun bindMenuInvoiceList(cartMenu: CartMenu) {
            itemView.menu_name.text = cartMenu.menu.menu_name
            itemView.text_quantity.text = cartMenu.quantity.toString()

            var totalPrice = cartMenu.quantity.times(cartMenu.menu.menu_price!!.toDouble())
            itemView.total_price.text = "${totalPrice}"
        }

    }

}