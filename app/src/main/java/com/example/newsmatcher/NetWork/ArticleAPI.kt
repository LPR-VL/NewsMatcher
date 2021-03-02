package com.example.newsmatcher.NetWork

import com.example.newsmatcher.NetWork.MyObject
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ArticleAPI {

    @GET("/v2/everything")
    fun getArticles(@Query("q")search:String,
                    @Query("from") date:String,
                    @Query("sortBy")published:String,
                    @Query("sources")sources:String,
                    @Query("pageSize")pageSize:String,
                    @Query("apiKey")apiKey:String):Call<MyObject>
}