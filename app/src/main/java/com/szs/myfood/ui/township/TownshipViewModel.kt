package com.szs.myfood.ui.township

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.szs.myfood.api.ApiClient
import com.szs.myfood.model.township.Township
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TownshipViewModel : ViewModel() {

    var township: MutableLiveData<Township> = MutableLiveData()
    var loadError: MutableLiveData<Boolean> = MutableLiveData()
    var loading: MutableLiveData<Boolean> = MutableLiveData()

    fun getTownship(): LiveData<Township> = township
    fun getError(): LiveData<Boolean> = loadError
    fun getLoading(): LiveData<Boolean> = loading

    val apiClient: ApiClient = ApiClient()

    fun loadTownship(){
        loading.value = true
        val call = apiClient.getTownship()
        call.enqueue(
            object : Callback<Township>{
                override fun onFailure(call: Call<Township>, t: Throwable) {
                    loadError.value = true
                    loading.value= false
                }

                override fun onResponse(
                    call: Call<Township>,
                    response: Response<Township>
                ) {
                    response?.isSuccessful.let {
                        loading.value = false
                        township.value = Township(
                            response?.body()?.townships?: emptyList())
                    }
                }

            }
        )
    }

}