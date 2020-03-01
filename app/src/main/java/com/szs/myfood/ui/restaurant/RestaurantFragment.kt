package com.szs.myfood.ui.restaurant


import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.szs.myfood.R
import com.szs.myfood.adapter.ImageSliderAdapter
import com.szs.myfood.adapter.RestaurantAdapter
import com.szs.myfood.model.getRestaurantByTownship.GetRestaurantByTownship
import com.szs.myfood.model.getRestaurantByTownship.Restaurant
import kotlinx.android.synthetic.main.fragment_restaurant.*
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class RestaurantFragment : Fragment(), RestaurantAdapter.ClickListener {

    private lateinit var restaurantAdapter: RestaurantAdapter
    private lateinit var viewlayoutManager: RecyclerView.LayoutManager
    lateinit var restaurantViewModel: RestaurantViewModel
    lateinit var restaurantViewModelFactory: RestaurantViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_restaurant, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = ImageSliderAdapter(activity!!)
        viewpager.adapter = adapter

        viewlayoutManager = LinearLayoutManager(this.context)
        restaurantAdapter = RestaurantAdapter()
        restaurant_recycler.adapter = restaurantAdapter
        restaurant_recycler.layoutManager = viewlayoutManager
        restaurantAdapter.setOnClickListener(this)

        val application = Objects.requireNonNull(activity)?.application
        val townshipArgs = RestaurantFragmentArgs.fromBundle(Objects.requireNonNull(arguments)!!).townshipName
        restaurantViewModelFactory = RestaurantViewModelFactory(application!!, townshipArgs)
        observeViewModel()

    }

    fun observeViewModel(){
        restaurantViewModel = ViewModelProvider(this,restaurantViewModelFactory).get(RestaurantViewModel::class.java)

        restaurantViewModel.loadRestaurant()

        restaurantViewModel.getRestaurantResult().observe(
            viewLifecycleOwner, Observer<GetRestaurantByTownship>{result ->
                restaurant_recycler.visibility = View.VISIBLE
                restaurantAdapter.updateRestaurantList(result.restaurants)
                Log.d("Restaurant result>>>>>>", result.toString())
            }
        )

        restaurantViewModel.getError().observe(
            viewLifecycleOwner, Observer<Boolean>{ isError ->
                if (isError){
                    txtError.visibility = View.VISIBLE
                    restaurant_recycler.visibility = View.GONE
                }else{
                    txtError.visibility = View.INVISIBLE
                }
            }
        )

        restaurantViewModel.getLoading().observe(
            viewLifecycleOwner, Observer<Boolean>{ isLoading ->
                loadingView.visibility = if ( isLoading )
                    View.VISIBLE
                else
                    View.INVISIBLE
                if (isLoading){
                    txtError.visibility = View.GONE
                    restaurant_recycler.visibility = View.GONE
                }
            }
        )
    }

    override fun onResume() {
        super.onResume()
        restaurantViewModel.loadRestaurant()
    }

    override fun onClick(restaurant: Restaurant) {
        if (!TextUtils.isEmpty(restaurant.user.name)){

//            val menuViewModel: MenuViewModel =
//                ViewModelProviders.of(activity!!).get(MenuViewModel::class.java)

            val action = RestaurantFragmentDirections.actionRestaurantFragmentToCategoryFragment(restaurant.id)
            view?.findNavController()?.navigate(action)
        }
    }

}
