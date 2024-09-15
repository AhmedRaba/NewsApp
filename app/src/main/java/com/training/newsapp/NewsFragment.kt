package com.training.newsapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.training.newsapp.databinding.FragmentNewsBinding
import com.training.newsapp.viewmodel.NewsViewModel

class NewsFragment : Fragment() {

    private lateinit var binding: FragmentNewsBinding
    private val viewModel: NewsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentNewsBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        viewModel.fetchArticles()
    }

    private fun setupRecyclerView() {
        viewModel.articles.observe(viewLifecycleOwner) {
            val adapter = NewsAdapter(it.articles)
            binding.rvNews.adapter = adapter
        }
    }


}