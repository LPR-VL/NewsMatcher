package com.example.newsmatcher

import android.util.Log
import com.example.newsmatcher.NetWork.Article
import kotlinx.coroutines.*
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import java.lang.Exception

class Parser {


   suspend fun getBodyString(article: Article):String {

       var string = ""
       try {
           var doc = Jsoup.connect(article.url)
               .userAgent("Chrome/88.0.4324.182")
               .referrer("http://www.google.com")
               .get()

           when (article.source.id) {
               "cnn" -> {
                   string = getString(doc, "zn-body__paragraph")
               }
               "bloomberg" -> {
                   string = getString2(doc, "middle-column")
               }
               "cbs-news" -> {
                   string = getString(doc, "content__body")
               }
               "new-york-magazine" -> {
                   string = getString(doc, "clay-paragraph")
               }
               "the-hill" -> {
                   string = getString2(doc, "field-item even")
               }
               "the-washington-post" -> {
                   string = getString(doc, "article-body")
               }
               "vice-news" -> {
                   string = getString2(doc, "abc__textblock size--article")
               }
               "fox-news" -> {
                   string = getString2(doc, "article-body")
               }
               "breitbart-news" -> {
                   string = getString2(doc, "entry-content")
               }
               "the-american-conservative" -> {
                   string = getString2(doc, "c-single-blog__content")
               }
               "the-washington-times" -> {
                   string = getString2(doc, "bigtext")
               }
           }
       } catch (e:Exception) {Log.i("Tag", "Exception Thrown")}
        Log.i("Tag", "Parsed Successfully")
        return string
    }

   private fun getString(doc:Document, className:String):String{
        var string:String = ""
        val elements = doc.getElementsByClass(className)
        for (cont: Element in elements) {
            string += cont.text()
        }
        return string
    }

   private fun getString2(doc:Document, className:String):String{
        var string:String = ""
        val elements = doc.getElementsByClass(className)
        for (cont: Element in elements.select("p")) {
            string += cont.text()
        }
        return string
    }


}

//CNN  - "zn-body__paragraph"
//Fox - "article-body"/ element.select ("p")
//Bloomberg - "middle-column" / element.select ("p")
//CBS News - "content__body"
//NYMag - "clay-paragraph"
//TheHill - "field-item even" / element.select ("p")
//Washington Post - "article-body"
// ViceNews - "abc__textblock size--article" / element.select ("p")
// Breitbart - "entry-content" / element.select ("p")
//The American Consevative - "c-single-blog__content"/ element.select ("p")
//The Wall Street Journal - "Doesn't work"
//The Washington Times - "bigtext"/ element.select ("p")




/*


 */