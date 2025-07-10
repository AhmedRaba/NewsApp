package com.silverkey.feature.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.silverkey.domain.model.Article
import com.silverkey.domain.utils.Result
import com.silverkey.feature.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by viewModels()
//    private val newsAdapter = NewsAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        observeNews()
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun observeNews() {
        viewModel.newsState.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> {
                    binding.textHome.text = "Loading..."
                }

                is Result.Success -> {
                    val firstArticle = result.data.firstOrNull()
                    if (firstArticle != null) {
                        showArticle(firstArticle)
                    } else {
                        binding.textHome.text = "No articles available."
                    }
                }

                is Result.Error -> {
                    binding.textHome.text = "Error: ${result.exception.message}"
                }

                is Result.Empty -> {
                    binding.textHome.text = "No articles available."
                }
            }
        }
    }

    private fun showArticle(article: Article) {
        binding.textHome.text = article.title
    }
}