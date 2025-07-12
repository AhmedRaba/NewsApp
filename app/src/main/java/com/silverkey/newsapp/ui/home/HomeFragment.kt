package com.silverkey.newsapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.silverkey.domain.utils.Result
import com.silverkey.newsapp.R
import com.silverkey.newsapp.databinding.FragmentHomeBinding
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
        return binding.root
    }

    private fun setupRecyclerView() {

        newsAdapter = NewsAdapter(
            onReadMoreClick = { article ->
                val action = HomeFragmentDirections.actionNavigationHomeToDetailsFragment(article)
                findNavController().navigate(action)
            },
            onFavoriteClick = { article ->

            }
        )
        binding.recylclerView.apply {
            adapter = newsAdapter
        }
    }

    private fun observeNews() {
        viewModel.newsState.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> {
                    // TODO: Show loading shimmer or progress bar
                }

                is Result.Success -> {
                    newsAdapter.submitList(result.data)
                }

                is Result.Error -> {
                    // TODO: Show error UI
                }

                is Result.Empty -> {
                    // TODO: Show empty state UI
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}