package com.training.newsapp

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import coil.load
import com.training.newsapp.databinding.FragmentNewsDetailsBinding
import java.time.Duration
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

class NewsDetailsFragment : Fragment() {

    private lateinit var binding: FragmentNewsDetailsBinding
    private val args: NewsDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentNewsDetailsBinding.inflate(inflater, container, false)

        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        setPublishedAt()  // Set the relative time for publishedAt
    }

    private fun setupViews() {
        Log.d("TAG", "setupViews: ${args.publishedAt}")
        binding.ivNewsDetails.load(args.imageUrl)
        binding.tvAuthorDetails.text = args.author
        binding.tvTitleDetails.text = args.title
        binding.tvDescriptionDetails.text = args.description

        binding.tvViewArticle.setOnClickListener {
            val url = args.url
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)
            startActivity(intent)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setPublishedAt() {
        val publishedAt = args.publishedAt
        val parsedDateTime =
            OffsetDateTime.parse(publishedAt, DateTimeFormatter.ISO_OFFSET_DATE_TIME)

        val currentDateTime = OffsetDateTime.now()

        val duration = Duration.between(parsedDateTime, currentDateTime)

        val seconds = duration.seconds
        val minutes = seconds / 60
        val hours = minutes / 60
        val days = hours / 24
        val months = days / 30
        val years = days / 365

        val timeAgo = when {
            years > 0 -> "$years year${if (years > 1) "s" else ""} ago"
            months > 0 -> "$months month${if (months > 1) "s" else ""} ago"
            days > 0 -> "$days day${if (days > 1) "s" else ""} ago"
            hours > 0 -> "$hours hour${if (hours > 1) "s" else ""} ago"
            minutes > 0 -> "$minutes minute${if (minutes > 1) "s" else ""} ago"
            else -> "Just now"
        }

        binding.tvPublishedAt.text = timeAgo
    }
}
