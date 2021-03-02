package com.example.newsapp

import android.graphics.drawable.Drawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.browser.customtabs.CustomTabsIntent
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest

import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject

class MainActivity : AppCompatActivity(), itemsclicked {
    private lateinit var mAdapter: Newslistadapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
      recyclerView.layoutManager=LinearLayoutManager(this)
       fetchdata()
         mAdapter=Newslistadapter(this)
        recyclerView.adapter=mAdapter
    }
    private fun fetchdata(){

        val url = "https://saurav.tech/NewsAPI/everything/bbc-news.json"

// Request a string response from the provided URL.
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url,null,
            Response.Listener {
                val newsJsonArray=it.getJSONArray("articles")
                val newsArray=ArrayList<News>()
                for(i in 0 until newsJsonArray.length() ) {
                    val newsJsonObject = newsJsonArray.getJSONObject(i)
                    val news = News(
                        newsJsonObject.getString("title"),
                                newsJsonObject.getString("author"),
                                newsJsonObject.getString("url"),
                                newsJsonObject.getString("urlToImage")
                    )
                    newsArray.add(news)


                }
                mAdapter.updateNews(newsArray)


            },
            Response.ErrorListener {
            })
//        fun getHeaders(): Map<String, String>? {
//            val headers = HashMap<String, String>()
//            //headers.put("Content-Type", "application/json");
//            headers["key"] = "Value"
//            return headers
//        }

// Add the request to the RequestQueue.
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)

    }


    override fun onitemsclicked(item: News) {

       val builder = CustomTabsIntent.Builder();
        val customTabsIntent = builder.build();
        customTabsIntent.launchUrl(this, Uri.parse(item.url));
    }

}