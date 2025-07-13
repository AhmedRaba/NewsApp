package com.silverkey.newsapp.ui.details

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.load
import com.silverkey.newsapp.R
import com.silverkey.newsapp.databinding.FragmentDetailsBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.TimeZone
import java.util.concurrent.TimeUnit


class DetailsFragment : Fragment() {

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!

    private val args: DetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupStatusBar()

        bindData()

        binding.icBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setupStatusBar() {
        val window = requireActivity().window
        WindowCompat.setDecorFitsSystemWindows(window, false)
        window.statusBarColor = Color.TRANSPARENT
        WindowInsetsControllerCompat(window, window.decorView).isAppearanceLightStatusBars = false
    }


    private fun bindData() = binding.apply {
        ivThumbnail.load(args.article.imageUrl) {
            crossfade(true)
            placeholder(R.drawable.iv_placeholder)
            error(R.drawable.iv_placeholder)
        }
        tvTitle.text = args.article.title
        tvDate.text = formatDaysAgo(args.article.publishedAt)
        tvAuthor.text = args.article.author
        tvDescription.text = args.article.description
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

    override fun onStop() {
        super.onStop()
        requireActivity().window?.let { window ->
            WindowCompat.setDecorFitsSystemWindows(window, true)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}