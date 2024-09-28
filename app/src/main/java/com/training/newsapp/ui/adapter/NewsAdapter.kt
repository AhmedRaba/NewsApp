package com.training.newsapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.size.Scale
import coil.size.ViewSizeResolver
import com.training.newsapp.R
import com.training.newsapp.data.api.model.Article
import com.training.newsapp.databinding.ItemNewsBinding

class NewsAdapter(private val articles: List<Article>) :
    RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {


    inner class NewsViewHolder(val binding: ItemNewsBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val binding = ItemNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return articles.size
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val article = articles[position]

        holder.binding.apply {
            tvTitleItem.text = article.title
            tvAuthorItem.text = article.source.name


            ivImageItem.load(article.urlToImage) {
                placeholder(R.drawable.iv_place_holder)
                crossfade(true)
                scale(Scale.FILL)
            }


        }
    }

}