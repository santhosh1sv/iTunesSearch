package com.altimetrik.itunessearch.apis

import com.altimetrik.itunessearch.models.iTunesModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiRequest {

    @GET("search?")
    fun getTunes(@Query("term") term: String ): Call<iTunesModel?>?


}