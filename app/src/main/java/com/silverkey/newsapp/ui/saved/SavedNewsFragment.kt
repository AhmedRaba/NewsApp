package com.silverkey.newsapp.ui.saved

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.silverkey.domain.utils.Result
import com.silverkey.newsapp.databinding.FragmentSavedNewsBinding
import com.silverkey.newsapp.ui.NewsAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SavedNewsFragment : Fragment() {

    private var _binding: FragmentSavedNewsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SavedNewsViewModel by viewModels()
    private lateinit var newsAdapter: NewsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentSavedNewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        observeSavedArticles()
    }

    private fun setupRecyclerView() {
        newsAdapter = NewsAdapter(
            onReadMoreClick = { article ->
                val action = SavedNewsFragmentDirections.actionNavigationSavedToDetailsFragment(article)
                findNavController().navigate(action)
            },
            onFavoriteClick = { article ->
                val currentList = newsAdapter.currentList.toMutableList()
                val removedIndex = currentList.indexOf(article)

                // 1. Remove article from list
                currentList.removeAt(removedIndex)
                newsAdapter.submitList(currentList.toList())

                Snackbar.make(binding.root, "Remove this article?", Snackbar.LENGTH_LONG)
                    .setAction("UNDO") {
                        // 2. Re-insert at same index
                        val restoredList = newsAdapter.currentList.toMutableList()
                        val safeIndex = removedIndex.coerceAtMost(restoredList.size)
                        restoredList.add(safeIndex, article)

                        newsAdapter.submitList(restoredList.toList()) {
                            // 3. Scroll to restored position after list updates
                            binding.recyclerView.scrollToPosition(safeIndex)
                        }
                    }
                    .addCallback(object : Snackbar.Callback() {
                        override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
                            if (event != DISMISS_EVENT_ACTION) {
                                viewModel.deleteArticle(article)
                            }
                        }
                    })
                    .show()
            }

        )
        binding.recyclerView.adapter = newsAdapter
    }

    private fun observeSavedArticles() {
        viewModel.savedArticlesState.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> {
                    binding.recyclerView.visibility = View.GONE
                    binding.emptyStateLayout.visibility = View.GONE
                }

                is Result.Success -> {
                    val articles = result.data
                    newsAdapter.submitList(articles)

                    val allUrls = articles.map { it.url }.toSet()
                    newsAdapter.updateSavedArticles(allUrls)

                    binding.recyclerView.visibility = if (articles.isNotEmpty()) View.VISIBLE else View.GONE
                    binding.emptyStateLayout.visibility = if (articles.isEmpty()) View.VISIBLE else View.GONE
                }

                is Result.Empty -> {
                    binding.recyclerView.visibility = View.GONE
                    binding.emptyStateLayout.visibility = View.VISIBLE
                }

                is Result.Error -> {
                    Toast.makeText(
                        requireContext(),
                        "Failed to load saved articles",
                        Toast.LENGTH_SHORT
                    ).show()

                    binding.recyclerView.visibility = View.GONE
                    binding.emptyStateLayout.visibility = View.VISIBLE
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getSavedArticles()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
