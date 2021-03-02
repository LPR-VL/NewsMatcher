package com.example.newsmatcher.Adapters

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.newsmatcher.ContentViewActivity
import com.example.newsmatcher.NetWork.Article
import com.example.newsmatcher.R

class MyAdapter (list: List<Article>, context:Context):RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var data = list
    var cont = context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_item, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
       return data.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder as MyViewHolder
        holder.bind(data[position], cont)
        holder.itemView.setOnClickListener { click(data[position].url) }
        holder.itemView.setOnLongClickListener { longClickAction(data[position]) }

    }
    fun click(link:String) {
        val URI = Uri.parse(link)
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = URI
        startActivity(cont, intent, null)
    }

    fun longClick(link: String):Boolean {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_TEXT, link)
        startActivity(cont,intent, null)
        return true
    }

    fun longClickAction(article: Article):Boolean {
        val intent = Intent(cont, ContentViewActivity::class.java)
        intent.putExtra("title", article.title)
        intent.putExtra("content", article.content)
        startActivity(cont,intent,null)
        return true
    }

}

class MyViewHolder(itemView:View):RecyclerView.ViewHolder(itemView) {
    val imageView: ImageView = itemView.findViewById(R.id.imageView)
    val titleView: TextView = itemView.findViewById(R.id.title_text)
    val sourceView: TextView = itemView.findViewById(R.id.source_text)
    val ideoView: TextView = itemView.findViewById(R.id.ideology_text)
    val dateView: TextView = itemView.findViewById(R.id.item_date_text)



    fun bind(item:Article, context:Context){

        val reqOpt = RequestOptions().placeholder(R.drawable.load). error(R.drawable.no_image)
        Glide.with(context)
            .applyDefaultRequestOptions(reqOpt)
            .load(item.urlToImage)
            .into(imageView)

        titleView.text = item.title
        sourceView.text = item.source.name
        ideoView.text = if (item.source.id=="fox-news" || item.source.id=="breitbart-news" || item.source.id=="the-american-conservative" ||
            item.source.id=="the-wall-street-journal" || item.source.id=="the-washington-times")
        {"Right-Wing"} else {"Left-Wing"}
        if (ideoView.text == "Right-Wing") {ideoView.setTextColor(Color.RED)} else {ideoView.setTextColor(Color.BLUE)}
        dateView.text = item.publishedAt
    }

}