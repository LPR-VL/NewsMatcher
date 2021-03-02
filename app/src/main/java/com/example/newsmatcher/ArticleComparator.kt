package com.example.newsmatcher

import android.R.attr.x
import android.util.Log
import com.example.newsmatcher.NetWork.Article
import java.util.Collections.swap


class ArticleComparator (var articleList:ArrayList<Article>) {
    private lateinit var leftArticles:ArrayList<Article>
    private lateinit var rightArticles:ArrayList<Article>
    private var rightIsFirst:Boolean = false

    lateinit var result: ArrayList<ArticlePair>

   private fun initialize() {
        leftArticles = ArrayList<Article>()
        rightArticles = ArrayList<Article>()
        result = ArrayList<ArticlePair>()

        for (art:Article in articleList) {
            if (art.source.id=="fox-news" || art.source.id=="breitbart-news" || art.source.id=="the-american-conservative" ||
                art.source.id=="the-wall-street-journal" || art.source.id=="the-washington-times") {rightArticles.add(art)} else {leftArticles.add(art)}
        }
    }

    fun compare() {
        initialize()
        if (rightArticles.size<leftArticles.size) {
            compareLists(rightArticles, leftArticles)
            rightIsFirst = true
        } else {compareLists(leftArticles, rightArticles)}
        Log.i("Tag", rightArticles.size.toString())
        Log.i("Tag", leftArticles.size.toString())
    }

    private  fun count(first:Article, second:ArrayList<Article>):Int{
        var num:Int = 0
        var points:Int = 0
        var resList = ArrayList<Int>()

        for (i:Int in 0..second.lastIndex) {
            if (compareTitles(first, second[i])>points) {points=compareTitles(first,second[i]); num = i}
        }
        for (i:Int in 0..second.lastIndex) {
            if (compareTitles(first, second[i])==points) {resList.add(i)}
        }
        if (resList.isEmpty()) {resList.add(num)}

        if (resList.size==1) {return num}
        else {
           num = chooseOne(first,second,resList)
           return num
        }
    }

    private  fun chooseOne(first:Article, second:ArrayList<Article>, nums:ArrayList<Int>):Int {
        var num:Int = nums[0]
        var points:Int = 0

        for (i in nums) {
            if (compareContent(first,second[i])>points) {points=compareContent(first,second[i]); num=i}
        }
        return num
    }

    private fun compareLists(first: ArrayList<Article>, second: ArrayList<Article>) {
        for (cont:Article in first) {
            if (!rightIsFirst) {
                result.add(ArticlePair(second[count(cont,second)], cont))
            } else {
                result.add(ArticlePair(cont,second[count(cont,second)]))
            }
        }
    }

     fun sort() {
         var newList = ArrayList<ArticlePair>()

         for (i in 1 until result.size) {
             var j=i
             while (j>0 && compareTitles(result[j-1].rightArticle, result[j-1].leftArticle)> compareTitles(result[j].rightArticle, result[j].leftArticle)) {
                 swap(result, j-1,j)
                 j--
             }
         }

         for (i in result.lastIndex downTo  0) {newList.add(result[i])}
         result = newList
         for (pair in result) {var str:String = compareTitles(pair.rightArticle, pair.leftArticle).toString(); Log.i("Tag", str) }
     }

    private fun compareTitles (first:Article, second:Article): Int {
        var points:Int =0
        val firstTitleArray = first.title.split(" ")
        val secondTitleArray = second.title.split(" ")

        for (i in firstTitleArray) { if (notEquals(i.toLowerCase())) {
            for (word in secondTitleArray) {if (i.toLowerCase() == word.toLowerCase()){
                points += 10
            }}
        }}
        return points
    }

    private fun compareContent(first:Article, second:Article): Int {
        var points:Int =0

        val firstContentArray = first.content.split(" ")
        var firstSelectedArray = ArrayList<String>()
        val secondContentArray = second.content.split(" ")
        var secondSelectedArray = ArrayList<String>()

        if (firstContentArray.isNotEmpty()) {
        for (word in firstContentArray) {
            if (word.toCharArray().isNotEmpty()) {
                if (word.first().isUpperCase()) {firstSelectedArray.add(word)}
            }
        }
        }
        if (secondContentArray.isNotEmpty()) {
            for (word in secondContentArray) {
                if (word.toCharArray().isNotEmpty()) {
                    if (word.first().isUpperCase()) {secondSelectedArray.add(word)}
                }
            }
        }

        for (i in firstSelectedArray) {
            if (notEquals(i.toLowerCase())) {
            for (word in secondSelectedArray) {if (i == word){
                points += 10
            }}
        } }
        return points
    }

    private fun notEquals(word:String):Boolean {
        var result = true
        val falseWords = arrayOf("the", "a","with", "in", "at", "of", "as", "to", "for", "that", "will", "was", "is", "are", "by", "on")
        for (str:String in falseWords) {if (word==str) {result = false}}
        return result
    }

}