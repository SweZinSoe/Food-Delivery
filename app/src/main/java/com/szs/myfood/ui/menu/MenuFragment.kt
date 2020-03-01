package com.szs.myfood.ui.menu


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.szs.myfood.R
import com.szs.myfood.ShoppingCart
import com.szs.myfood.adapter.MenuAdapter
import com.szs.myfood.model.getMenuByRestaurant.GetMenuByRestaurant
import io.paperdb.Paper
import kotlinx.android.synthetic.main.fragment_menu.*
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class MenuFragment : Fragment(){

    private lateinit var menuAdapter: MenuAdapter
    private lateinit var viewLayoutmanager: RecyclerView.LayoutManager
    lateinit var menuViewModel: MenuViewModel
    lateinit var menuViewModelFactory: MenuViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Paper.init(context)
        var root = inflater.inflate(R.layout.fragment_menu, container, false)
        var btnCart = root.findViewById<Button>(R.id.btn_cart)
        btnCart.setOnClickListener { view: View ->
            view.findNavController().navigate(R.id.action_categoryFragment_to_invoiceFragment)
        }
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLayoutmanager = LinearLayoutManager(this.context)
        menuAdapter = MenuAdapter()
        menuRecycler.adapter = menuAdapter
        menuRecycler.layoutManager = viewLayoutmanager

        val application = Objects.requireNonNull(activity)?.application
        val restaurantIDArgs = MenuFragmentArgs.fromBundle(Objects.requireNonNull(arguments)!!).restaurantID
        menuViewModelFactory = MenuViewModelFactory(application!!, restaurantIDArgs)
        observeViewModel()
    }

    fun observeViewModel() {
        menuViewModel = ViewModelProvider(this, menuViewModelFactory).get(MenuViewModel::class.java)

        menuViewModel.loadMenuResults()

        menuViewModel.getMenuResult().observe(
            viewLifecycleOwner, Observer<GetMenuByRestaurant> { result ->
                menuRecycler.visibility = View.VISIBLE
                menuAdapter.updateMenuList(result.menus)
                Log.d("Result >>>>", result.menus.toString())
            }
        )

        menuViewModel.getError().observe(
            viewLifecycleOwner, Observer<Boolean> { isError ->
                if (isError) {
                    txtError.visibility = View.VISIBLE
                    menuRecycler.visibility = View.GONE
                } else {
                    txtError.visibility = View.INVISIBLE
                }
            }
        )

        menuViewModel.getLoading().observe(
            viewLifecycleOwner, Observer<Boolean> { isLoading ->
                loadingView.visibility =
                    if (isLoading)
                        View.VISIBLE
                    else
                        View.INVISIBLE
                if (isLoading) {
                    txtError.visibility = View.GONE
                    menuRecycler.visibility = View.GONE
                }
            }
        )
    }

    override fun onResume() {
        super.onResume()
        menuViewModel.loadMenuResults()
    }
}
