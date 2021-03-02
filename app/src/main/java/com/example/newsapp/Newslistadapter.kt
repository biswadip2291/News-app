package com.example.newsapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class Newslistadapter( private val listener :itemsclicked) : RecyclerView.Adapter<viewholder>() {
    private val items:ArrayList<News> = ArrayList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewholder {
val view = LayoutInflater.from(parent.context).inflate(R.layout.newsitem,parent,false)
        val newsviewholder=viewholder(view)
        view.setOnClickListener{
            listener.onitemsclicked(items[newsviewholder.adapterPosition])
        }
        return newsviewholder

    }

    override fun onBindViewHolder(holder: viewholder, position: Int) {
val currentview=items[position]
        holder.titleview.text=currentview.title
      holder.authorview.text=currentview.author
        Glide.with(holder.itemView.context).load(currentview.urlToImage).into(holder.image)
    }

    override fun getItemCount(): Int {
return items.size
    }
    fun updateNews(updateNews: ArrayList<News>){
        items.clear()
        items.addAll(updateNews)
        notifyDataSetChanged()
    }
}
class viewholder(itemView: View) : RecyclerView.ViewHolder(itemView)
{
    val titleview :TextView=itemView.findViewById(R.id.title)
    val image :ImageView=itemView.findViewById(R.id.imageL)
    val authorview:TextView=itemView.findViewById(R.id.author)
}
interface itemsclicked
{
    fun onitemsclicked(item: News)
}

