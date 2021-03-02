package com.example.newsmatcher.Adapters

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.newsmatcher.ArticlePair
import com.example.newsmatcher.R

class RecyclerAdapter (list: List<ArticlePair>, context: Context):RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var data = list
    var cont = context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
       var view = LayoutInflater.from(parent.context).inflate(R.layout.new_recyclerview_item, parent, false)
        return NewViewHolder(view)
    }

    override fun getItemCount(): Int {
       return data.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder as NewViewHolder
        holder.bind(data[position], cont)
        holder.setListeners(data[position], cont)


    }

}

class NewViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {

  private val leftImageView = itemView.findViewById<ImageView>(R.id.leftImageView)
    private val leftSourceTextView = itemView.findViewById<TextView>(R.id.leftSource)
    private val leftIdeologyTextView = itemView.findViewById<TextView>(R.id.leftIdeo)
    private val leftDateTextView = itemView.findViewById<TextView>(R.id.leftDate)
    private val leftTitleTextView = itemView.findViewById<TextView>(R.id.leftTitle)

    private val leftCardView = itemView.findViewById<CardView>(R.id.leftView)
   // val leftContentTextView = itemView.findViewById<TextView>(R.id.leftContent)

    private val rightImageView = itemView.findViewById<ImageView>(R.id.rightImageView)
    private val rightSourceTextView = itemView.findViewById<TextView>(R.id.rightSource)
    private val rightIdeologyTextView = itemView.findViewById<TextView>(R.id.rightIdeo)
    private val rightDateTextView = itemView.findViewById<TextView>(R.id.rightDate)
    private val rightTitleTextView = itemView.findViewById<TextView>(R.id.rightTitle)

    private val rightCardView = itemView.findViewById<CardView>(R.id.rightCardView)
   // val rightContentTextView = itemView.findViewById<TextView>(R.id.rightContent)


    fun bind (item: ArticlePair, context: Context) {
        val reqOpt = RequestOptions().placeholder(R.drawable.load). error(R.drawable.no_image)
        Glide.with(context)
            .applyDefaultRequestOptions(reqOpt)
            .load(item.leftArticle.urlToImage)
            .into(leftImageView)

        leftSourceTextView.text = item.leftArticle.source.name
        leftIdeologyTextView.text = if (item.leftArticle.source.id=="fox-news" || item.leftArticle.source.id=="breitbart-news" || item.leftArticle.source.id=="the-american-conservative" ||
            item.leftArticle.source.id=="the-wall-street-journal" || item.leftArticle.source.id=="the-washington-times")
        {"Right-Wing"} else {"Left-Wing"}
        if (leftIdeologyTextView.text == "Right-Wing") {leftIdeologyTextView.setTextColor(Color.RED)} else {leftIdeologyTextView.setTextColor(
            Color.BLUE)}

        leftDateTextView.text = item.leftArticle.publishedAt
        leftTitleTextView.text = item.leftArticle.title
       // leftContentTextView.text = item.leftArticle.content

        Glide.with(context)
            .applyDefaultRequestOptions(reqOpt)
            .load(item.rightArticle.urlToImage)
            .into(rightImageView)

         rightSourceTextView.text = item.rightArticle.source.name

        rightIdeologyTextView.text = if (item.rightArticle.source.id=="fox-news" || item.rightArticle.source.id=="breitbart-news" || item.rightArticle.source.id=="the-american-conservative" ||
            item.rightArticle.source.id=="the-wall-street-journal" || item.rightArticle.source.id=="the-washington-times")
        {"Right-Wing"} else {"Left-Wing"}
        if (rightIdeologyTextView.text == "Right-Wing") {rightIdeologyTextView.setTextColor(Color.RED)} else {rightIdeologyTextView.setTextColor(
            Color.BLUE)}

        rightDateTextView.text = item.rightArticle.publishedAt
        rightTitleTextView.text = item.rightArticle.title
     //   rightContentTextView.text = item.rightArticle.content

    }

    fun setListeners(item: ArticlePair,context: Context) {
        leftCardView.setOnClickListener { click(item.leftArticle.url, context) }
        rightCardView.setOnClickListener { click(item.rightArticle.url, context)  }

        leftCardView.setOnLongClickListener { longClick(item.leftArticle.url, context) }
        rightCardView.setOnLongClickListener { longClick(item.rightArticle.url, context) }
    }

   private fun click(link:String, context: Context) {
        val URI = Uri.parse(link)
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = URI
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        ContextCompat.startActivity(context, intent, null)
    }

   private fun longClick(link: String, context: Context):Boolean {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        intent.putExtra(Intent.EXTRA_TEXT, link)
        ContextCompat.startActivity(context, intent, null)
        return true
    }

}