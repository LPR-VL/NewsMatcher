package com.example.newsmatcher

import android.app.Application
import android.util.Log
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.AndroidViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsmatcher.Adapters.RecyclerAdapter
import com.example.newsmatcher.Adapters.TopSpacingItem

class ListViewModel (application: Application):AndroidViewModel(application) {
    val app = application
    lateinit var thisAdapter:RecyclerAdapter
    lateinit var decoration:TopSpacingItem
    lateinit var layoutManager:LinearLayoutManager


    init {
        val artComp = ArticleComparator(artList)
        artComp.compare()
        artComp.sort()
        initAdapter(artComp.result)
    }

    fun initAdapter(result: ArrayList<ArticlePair>) {
        thisAdapter = RecyclerAdapter(result, app.baseContext)
        decoration = TopSpacingItem(20)
        layoutManager = LinearLayoutManager(app.baseContext)
        layoutManager.orientation = LinearLayoutManager.HORIZONTAL
    }



}
