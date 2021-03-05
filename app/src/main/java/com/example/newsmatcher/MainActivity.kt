package com.example.newsmatcher

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Adapter
import com.example.newsmatcher.NetWork.Article
import com.example.newsmatcher.NetWork.ArticleAPI
import com.example.newsmatcher.NetWork.MyObject
import com.example.newsmatcher.NetWork.ServiceBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

var artList = ArrayList<Article>()
var parser = Parser()

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //adapter = MyAdapter(artList, this)
        setContentView(R.layout.activity_main)
        Log.i("Lifecycle", "MainActivity: onCreate")
    }

}