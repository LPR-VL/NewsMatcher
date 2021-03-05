package com.example.newsmatcher

import android.app.Application
import android.app.PendingIntent.getActivity
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.example.newsmatcher.NetWork.Article
import com.example.newsmatcher.NetWork.ArticleAPI
import com.example.newsmatcher.NetWork.MyObject
import com.example.newsmatcher.NetWork.ServiceBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList

class SearchViewModel(application: Application) : AndroidViewModel(application) {
    val app = application
    var search = ""
    var date = getTodaysDate()
    val published = "publishedAt"
    val sources = "bloomberg, cnn, cbs-news, new-york-magazine, the-hill, the-washington-post, vice-news, fox-news, breitbart-news, the-american-conservative, the-washington-times"
    val pageSize = "100"
    val apiKey = "99f699c6035c453d91fcf12e64212f2a"


    private fun getTodaysDate(): String {
        val calendar = GregorianCalendar()
        val dateString =
            calendar[Calendar.YEAR].toString() + "-" + ((calendar[Calendar.MONTH]) + 1).toString() + "-" + calendar[Calendar.DAY_OF_MONTH]
        return dateString
    }

    fun makeRequest(){
        val request = ServiceBuilder.buildService(ArticleAPI::class.java)
        val call = request.getArticles(search, date, published, sources, pageSize,apiKey)

        call.enqueue(object : Callback<MyObject> {
            override fun onFailure(call: Call<MyObject>, t: Throwable) {
                Toast.makeText(app.applicationContext, "Loading Error", Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<MyObject>, response: Response<MyObject>) {
                if (response.isSuccessful) {
                    Log.i("Tag", "Successfull")
                    val jsonObj = response.body()
                    Log.i("Tag", jsonObj!!.totalResults)
                    artList = jsonObj.articles

                 //   ListFragment.ThisAdapter.data= artList
                 //   ListFragment.ThisAdapter.notifyDataSetChanged()
                } else {
                    Toast.makeText(app.applicationContext, "Loading Error", Toast.LENGTH_LONG).show()
                }
            }
        })
    }

}