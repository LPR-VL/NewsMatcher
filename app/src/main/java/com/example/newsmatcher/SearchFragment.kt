package com.example.newsmatcher

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.navigation.fragment.findNavController
import com.example.newsmatcher.NetWork.Article
import kotlinx.coroutines.*
import java.util.*
import kotlin.collections.ArrayList

class SearchFragment : Fragment(), CalendarView.OnDateChangeListener {
    lateinit var searchField:EditText
    lateinit var dateField:TextView
    lateinit var searchButton:Button
    lateinit var calendarView:CalendarView
    lateinit var progressBar: ProgressBar
    lateinit var loadingText: TextView
    lateinit var titleCheckSwitch:Switch
    lateinit var dateFieldText:TextView

    var checkTitle = true


    companion object {
        fun newInstance() = SearchFragment()
    }

    private lateinit var viewModel: SearchViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.search_fragment, container, false)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(SearchViewModel::class.java)
    }

    override fun onStart() {
        super.onStart()
        initViews()

    }

    fun onClickButton(view: View){
        hideViews()
        CoroutineScope(Dispatchers.IO).launch {
            viewModel.search = searchField.text.toString()
            viewModel.makeRequest()
            delay(5000)
            if (checkTitle) {artList = titleCheck()}
            for (i:Int in 0.. artList.lastIndex) {
                Log.i("Tag", "Art "+ artList[i].source.name)
                Log.i("Tag", "Loop $i")
              artList[i].content = parser.getBodyString(artList[i])
            }
            goToListInMain()
        }
    }

    private suspend fun goToListInMain() {
        withContext(Dispatchers.Main) {
         goToListFragment()
        }
    }

    private fun goToListFragment(){
        findNavController().navigate(R.id.action_searchFragment_to_listFragment)
    }

    private fun initViews(){
         searchField = view?.findViewById(R.id.search_field)!!
         dateField = view?.findViewById(R.id.date_text)!!
         dateField.text = viewModel.date
         searchButton = view?.findViewById(R.id.search_button)!!
         searchButton?.setOnClickListener { onClickButton(it) }
         calendarView = view?.findViewById(R.id.calendarView)!!
         calendarView.setOnDateChangeListener(this)
         progressBar = view?.findViewById(R.id.progressBar)!!
         loadingText = view?.findViewById(R.id.loadingText)!!
         titleCheckSwitch = view?.findViewById(R.id.title_check_switch)!!
         dateFieldText = view?.findViewById(R.id.textView2)!!
    }

    private fun hideViews(){
        searchButton.isClickable = false
        searchButton.isEnabled  = false
        titleCheckSwitch.isClickable = false
        titleCheckSwitch.isEnabled = false
        calendarView.isClickable = false
        calendarView.isEnabled = false
        searchField.isEnabled = false
        calendarView.visibility = View.INVISIBLE
        dateFieldText.visibility = View.INVISIBLE
        dateField.visibility = View.INVISIBLE


        progressBar.visibility = View.VISIBLE
        loadingText.visibility = View.VISIBLE
        searchButton.isClickable=false
        checkTitle = titleCheckSwitch.isChecked


    }

    private fun titleCheck():ArrayList<Article> {
        val newList = ArrayList<Article>()
        val searchString = searchField.text.toString()
        val words = searchString.split(" ")
        val wordsSize = words.size
        when (wordsSize) {
            1->{
                for (cont:Article in artList) {if (cont.title.contains(words[0], true)) {newList.add(cont)}}
            }
            2->{
                for (cont:Article in artList)
                {if (cont.title.contains(words[0], true) &&
                    cont.title.contains(words[1], true)) {newList.add(cont)}}
            }
            3->{
                for (cont:Article in artList)
                {if (cont.title.contains(words[0], true) &&
                     cont.title.contains(words[1], true) &&
                     cont.title.contains(words[2], true))
                        {newList.add(cont)}}
            }
            4->{
                for (cont:Article in artList)
                {if (cont.title.contains(words[0], true) &&
                    cont.title.contains(words[1], true) &&
                    cont.title.contains(words[2], true) &&
                    cont.title.contains(words[3], true))
                {newList.add(cont)}}
            }
            5->{
                for (cont:Article in artList)
                {if (cont.title.contains(words[0], true) &&
                    cont.title.contains(words[1], true) &&
                    cont.title.contains(words[2], true) &&
                    cont.title.contains(words[3], true) &&
                    cont.title.contains(words[4], true))
                {newList.add(cont)}}
            }
        }

        return newList
    }

    override fun onSelectedDayChange(view: CalendarView, year: Int, month: Int, dayOfMonth: Int) {
        val today = GregorianCalendar()
        val lmonth = if  (today[Calendar.MONTH]==0) {11} else {today[Calendar.MONTH]-1}
        val lastDay = GregorianCalendar(today[Calendar.YEAR], lmonth, today[Calendar.DAY_OF_MONTH])
        val selectedDay = GregorianCalendar(year, month, dayOfMonth)

        if (selectedDay.before(lastDay)) { Toast.makeText(this.context, "Max - one month from today", Toast.LENGTH_LONG).show()}
        else {

            val smonth = if (month == 12) {
                1
            } else {
                month + 1
            }
            val selectedDayString = "$year-$smonth-$dayOfMonth"
            viewModel.date = selectedDayString
           // Log.i("Tag", viewModel.date)
            initViews()
        }
    }

}