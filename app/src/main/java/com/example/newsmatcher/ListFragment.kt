package com.example.newsmatcher

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsmatcher.Adapters.RecyclerAdapter
import com.example.newsmatcher.Adapters.TopSpacingItem

class ListFragment : Fragment() {
    lateinit var recyclerView: RecyclerView
    lateinit var viewModel:ListViewModel




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.list_fragment, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ListViewModel::class.java)
        Log.i("Lifecycle", "ListFragment: onActivityCreated")
    }


    override fun onStart() {
        super.onStart()
       // viewModel = ViewModelProvider(this).get(ListViewModel::class.java)
        initRecycler()
        Log.i("Lifecycle", "ListFragment: onStart")
    }


    fun initRecycler() {
        recyclerView = view?.findViewById(R.id.recycler_view)!!
        val recAdapter = viewModel.thisAdapter
        recyclerView.apply {
            adapter = recAdapter
            addItemDecoration(viewModel.decoration)
            layoutManager = viewModel.layoutManager
        }
    }

    override fun onStop() {
        super.onStop()
        Log.i("Lifecycle", "ListFragment: onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i("Lifecycle", "ListFragment: onDestroy")
    }


}