package com.example.newsmatcher.NetWork


data class MyObject(
    val status:String,
    val totalResults:String,
    val articles:ArrayList<Article>
)

data class Article(
    val source: Source,
    val author: String,
    val title: String,
    val description: String,
    val url: String,
    val urlToImage: String,
    val publishedAt: String,
    var content: String
)

data class Source(
    val id:String,
    val name:String
)