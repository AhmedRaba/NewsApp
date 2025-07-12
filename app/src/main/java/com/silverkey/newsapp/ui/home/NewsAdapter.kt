package com.silverkey.newsapp.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.silverkey.domain.model.Article
import com.silverkey.newsapp.R
import com.silverkey.newsapp.databinding.ItemNewsArticleBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.TimeZone
import java.util.concurrent.TimeUnit

class NewsAdapter(
    private val onClick: (Article) -> Unit,
) : ListAdapter<Article, NewsAdapter.NewsViewHolder>(DiffCallback) {

    inner class NewsViewHolder(private val binding: ItemNewsArticleBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(article: Article) = with(binding) {
            tvTitle.text = article.title
            tvDate.text = formatDaysAgo(article.publishedAt)
            tvDescription.text = article.description

            ivThumbnail.load(article.imageUrl) {
                crossfade(true)
                placeholder(R.drawable.iv_placeholder)
                error(R.drawable.iv_placeholder)
            }

            btnReadMore.setOnClickListener { onClick(article) }
            btnFavorite.setOnClickListener { onClick(article) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val binding = ItemNewsArticleBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return NewsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


    fun formatDaysAgo(publishedAt: String?): String {
        if (publishedAt.isNullOrEmpty()) return ""

        return try {
            val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
            sdf.timeZone = TimeZone.getTimeZone("UTC")
            val date = sdf.parse(publishedAt) ?: return ""

            val now = Calendar.getInstance()
            val published = Calendar.getInstance()
            published.time = date

            val diffMillis = now.timeInMillis - published.timeInMillis
            val days = TimeUnit.MILLISECONDS.toDays(diffMillis)
            val months = days / 30
            val years = days / 365

            when {
                years >= 1 -> "$years year${if (years > 1) "s" else ""} ago"
                months >= 1 -> "$months month${if (months > 1) "s" else ""} ago"
                days > 1 -> "$days days ago"
                days == 1L -> "Yesterday"
                else -> "Today"
            }
        } catch (e: Exception) {
            ""
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.title == newItem.title
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }
    }
}