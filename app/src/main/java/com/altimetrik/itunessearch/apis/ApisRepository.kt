package com.altimetrik.itunessearch.apis

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.altimetrik.itunessearch.models.Result
import com.altimetrik.itunessearch.models.iTunesModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object ApisRepository {
    var apiRequest: ApiRequest? = null
    var iTunes: MutableLiveData<List<Result>>? = null

    init {
        val retrofit = ApiClient.getClient()
        apiRequest = retrofit?.create(ApiRequest::class.java)
    }


    fun getiTunes(term:String): LiveData<List<Result>>?{
        iTunes= MutableLiveData()
        apiRequest?.getTunes(term)?.enqueue(object: Callback<iTunesModel?>{
            override fun onResponse(call: Call<iTunesModel?>, response: Response<iTunesModel?>) {
                try {
                    val iTunesModel = response.body()
                    if (iTunesModel?.resultCount!=0 && iTunesModel!!.results.size != 0) {
                        iTunes?.value =iTunesModel.results
                    } else
                        iTunes?.value = null

                } catch (e: Exception) {
                    iTunes?.value = null
                }
            }

            override fun onFailure(call: Call<iTunesModel?>, t: Throwable) {
                iTunes?.value = null
            }
        })
        return iTunes
    }
















}