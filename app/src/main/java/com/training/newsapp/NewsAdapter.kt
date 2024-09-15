package com.training.newsapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.training.newsapp.databinding.ItemNewsBinding
import com.training.newsapp.model.articles.Article
import java.util.Calendar

class NewsAdapter(val articles: List<Article>) :
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
            tvAuthorItem.text = article.author

            Glide.with(holder.itemView).load(article.urlToImage).centerCrop().into(ivImageItem)


        }
    }

}