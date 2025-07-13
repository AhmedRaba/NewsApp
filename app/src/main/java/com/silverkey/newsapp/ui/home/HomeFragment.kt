package com.silverkey.newsapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.silverkey.domain.utils.Result
import com.silverkey.newsapp.databinding.FragmentHomeBinding
import com.silverkey.newsapp.ui.NewsAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by viewModels()
    private lateinit var newsAdapter: NewsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        setupRecyclerView()
        observeNews()
        observeSavedStatuses()

        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.fetchNews()
        }

        binding.btnRetry.setOnClickListener {
            viewModel.fetchNews()
        }

        return binding.root
    }

    private fun setupRecyclerView() {

        newsAdapter = NewsAdapter(
            onReadMoreClick = { article ->
                val action = HomeFragmentDirections.actionNavigationHomeToDetailsFragment(article)
                findNavController().navigate(action)
            },
            onFavoriteClick = { article ->
                viewModel.toggleArticleSaved(article)
            }
        )
        binding.recyclerView.apply {
            adapter = newsAdapter
        }
    }

    private fun observeNews() {
        viewModel.newsState.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> {
                    binding.errorLayout.visibility = View.GONE
                    binding.tvError.text = ""
                    binding.shimmerLayout.visibility = View.VISIBLE
                    binding.recyclerView.visibility = View.GONE
                    binding.swipeRefreshLayout.isRefreshing = false
                }

                is Result.Success -> {
                    newsAdapter.submitList(result.data)
                    binding.shimmerLayout.visibility = View.GONE
                    binding.recyclerView.visibility = View.VISIBLE
                    binding.errorLayout.visibility = View.GONE
                    binding.tvError.text = ""
                    binding.swipeRefreshLayout.isRefreshing = false

                    result.data.forEach { article ->
                        viewModel.checkIfArticleSaved(article.url)
                    }
                }

                is Result.Error -> {
                    binding.shimmerLayout.visibility = View.GONE
                    binding.recyclerView.visibility = View.GONE
                    binding.errorLayout.visibility = View.VISIBLE
                    binding.swipeRefreshLayout.isRefreshing = false
                    binding.tvError.text = result.exception.message ?: "An error occurred"
                }

                is Result.Empty -> {
                    binding.shimmerLayout.visibility = View.GONE
                    binding.recyclerView.visibility = View.VISIBLE
                    binding.errorLayout.visibility = View.GONE
                    binding.tvError.text = ""
                    binding.swipeRefreshLayout.isRefreshing = false
                }
            }
        }
    }
    private fun observeSavedStatuses() {
        viewModel.savedStatuses.observe(viewLifecycleOwner) { savedMap ->

            val savedUrls = savedMap.filter { it.value }.keys
            newsAdapter.updateSavedArticles(savedUrls)
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}