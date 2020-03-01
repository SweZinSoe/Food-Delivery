package com.szs.myfood.ui.getCustomerDetails


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.findNavController

import com.szs.myfood.R
import com.szs.myfood.ShoppingCart
import org.w3c.dom.Text

/**
 * A simple [Fragment] subclass.
 */
class GetCustomerDetailsFragment : Fragment(){

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var root = inflater.inflate(R.layout.fragment_get_customer_details, container, false)
        var customerName = root.findViewById<EditText>(R.id.customerName)
        var customerPhNo = root.findViewById<EditText>(R.id.customerPhno)
        var customerAddress = root.findViewById<EditText>(R.id.customerAddress)
        var btnConfirmInfo = root.findViewById<Button>(R.id.btnConfirmInfo)
        btnConfirmInfo.setOnClickListener{ view: View ->

            if (customerName.text.isEmpty() || customerPhNo.text.isEmpty() || customerAddress.text.isEmpty()) {
                Toast.makeText(context, "Fill all of these Requirements Please!", Toast.LENGTH_LONG)
                    .show()
            }

            else {
                ShoppingCart.deleteShoppingCart()
                view.findNavController()
                    .navigate(R.id.action_getCustomerDetailsFragment_to_orderSuccessFragment)
            }
        }
        return root
    }
}
