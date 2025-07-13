package com.silverkey.newsapp.ui

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
    private val onReadMoreClick: (Article) -> Unit,
    private val onFavoriteClick: (Article) -> Unit,
) : ListAdapter<Article, NewsAdapter.ArticleViewHolder>(DIFF_CALLBACK) {

    private var savedArticles: Set<String> = emptySet()

    fun updateSavedArticles(saved: Set<String>) {
        savedArticles = saved
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val binding =
            ItemNewsArticleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ArticleViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article = getItem(position)
        holder.bind(article)
    }

    inner class ArticleViewHolder(private val binding: ItemNewsArticleBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(article: Article) {
            binding.ivThumbnail.load(article.imageUrl)
            binding.tvTitle.text = article.title
            binding.tvDate.text = formatDaysAgo(article.publishedAt)
            binding.tvDescription.text = article.description

            val isSaved = savedArticles.contains(article.url)

            binding.btnFavorite.setImageResource(
                if (isSaved) R.drawable.ic_save_filled else R.drawable.ic_saved_active
            )

            binding.btnFavorite.setOnClickListener {
                onFavoriteClick(article)
            }

            binding.btnReadMore.setOnClickListener {
                onReadMoreClick(article)
            }
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Article>() {
            override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean =
                oldItem.url == newItem.url

            override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean =
                oldItem == newItem
        }
    }


    private fun formatDaysAgo(publishedAt: String?): String {
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

}
