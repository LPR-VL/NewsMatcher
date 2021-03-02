package com.example.newsmatcher

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class ContentViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_content_view)
        findViewById<TextView>(R.id.content_title_text).text = intent.getStringExtra("title")
        findViewById<TextView>(R.id.content_text).text = intent.getStringExtra("content")
    }
}