package com.training.newsapp.ui.frags

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.training.newsapp.R
import com.training.newsapp.Resource
import com.training.newsapp.data.api.model.Article
import com.training.newsapp.data.api.model.ArticlesResponse
import com.training.newsapp.data.api.model.Source
import com.training.newsapp.databinding.FragmentNewsBinding
import com.training.newsapp.ui.adapter.NewsAdapter
import com.training.newsapp.ui.viewmodel.NewsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewsFragment : Fragment() {

    private lateinit var binding: FragmentNewsBinding
    private val viewModel: NewsViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentNewsBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        setupRecyclerView()
        observeViewModel()
        setupRetryButton()
        setupDrawer()
        setupSearch()


        fetchSources()

    }

    private fun setupRecyclerView() {
        binding.rvNews.adapter = NewsAdapter(emptyList()) {
            navigateToNewsDetails(it)
        }
    }

    private fun observeViewModel() {


        viewModel.articles.observe(viewLifecycleOwner) { articleResponse ->
            when (articleResponse) {
                is Resource.Loading -> showLoading(true)
                is Resource.Success -> {
                    showLoading(false)
                    updateNewsList(articleResponse.data!!)
                    toggleRetryButton(false)
                }

                is Resource.Error -> {
                    showLoading(false)
                    showError(articleResponse.message!!)
                    toggleRetryButton(true)
                }
            }
        }

        viewModel.sources.observe(viewLifecycleOwner) { sourcesResponse ->
            when (sourcesResponse) {
                is Resource.Loading -> showLoading(true)
                is Resource.Success -> {
                    showLoading(false)
                    showTabs(sourcesResponse.data!!.sources)
                    toggleRetryButton(false)
                }

                is Resource.Error -> {
                    showLoading(false)
                    showError(sourcesResponse.message!!)
                    toggleRetryButton(true)
                }
            }
        }

    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
            binding.rvNews.visibility = View.GONE
            binding.btnRetry.visibility = View.GONE
        } else {
            binding.progressBar.visibility = View.GONE
            binding.rvNews.visibility = View.VISIBLE
        }

    }


    private fun setupRetryButton() {
        binding.btnRetry.setOnClickListener {
            val selectedTab = binding.tabsNews.getTabAt(binding.tabsNews.selectedTabPosition)
            val source = selectedTab?.tag as? String
            if (source != null) {
                fetchArticlesBySource(source)
            }
            fetchSources()
            toggleRetryButton(false)
        }
    }

    private fun toggleRetryButton(show: Boolean) {
        if (show) {
            binding.btnRetry.visibility = View.VISIBLE
            binding.rvNews.visibility = View.GONE
        } else {
            binding.btnRetry.visibility = View.GONE
            binding.rvNews.visibility = View.VISIBLE
        }
    }


    private fun updateNewsList(response: ArticlesResponse) {
        val adapter = NewsAdapter(response.articles) {
            navigateToNewsDetails(it)
        }
        binding.rvNews.adapter = adapter
    }


    private fun fetchSources() {
        val category = arguments?.getString("category")
        viewModel.fetchSources(category.toString())
        Log.e("fetchSources", "fetchSources: $category")
    }

    private fun fetchArticlesBySource(source: String, query: String = "") {
        Log.e("SOURCE", "SOURCE: $source")
        viewModel.fetchArticlesBySource(source = source, query = query)
    }


    private fun showTabs(sources: List<Source>) {

        sources.forEach {
            val tab = binding.tabsNews.newTab().setText(it.name)
            tab.tag = it.id
            binding.tabsNews.addTab(tab)
        }

        for (i in 0 until binding.tabsNews.tabCount) {
            val tabView = (binding.tabsNews.getChildAt(0) as ViewGroup).getChildAt(i)
            val layoutParams = tabView.layoutParams as ViewGroup.MarginLayoutParams
            layoutParams.setMargins(16, 0, 0, 16)
            tabView.layoutParams = layoutParams
        }

        val firstTab = binding.tabsNews.getTabAt(0)
        binding.tabsNews.selectTab(firstTab)
        fetchArticlesBySource(firstTab?.tag.toString())


        binding.tabsNews.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.tag?.let {
                    fetchArticlesBySource(it.toString())
                }

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                tab?.tag?.let { fetchArticlesBySource(it.toString()) }
            }
        })

    }


    private fun setupSearch() {
        binding.ivSearch.setOnClickListener {
            binding.searchLayout.visibility = View.VISIBLE
        }

        binding.searchView.setOnQueryTextListener(object : OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                binding.searchLayout.visibility = View.GONE
                if (query != null) {
                    fetchArticlesBySource("", query = query)
                    Log.e("onQueryTextSubmit", "onQueryTextSubmit: $query")
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })


    }


    private fun setupDrawer() {
        binding.ivDrawer.setOnClickListener {
            binding.drawerLayout.openDrawer(binding.navViewNews)
        }

        binding.navViewNews.setNavigationItemSelectedListener { menuItem ->

            when (menuItem.itemId) {
                R.id.menu_categories -> {
                    findNavController().navigate(R.id.action_newsFragment_to_categoriesFragment)
                }

                R.id.menu_settings -> {
                    findNavController().navigate(R.id.action_newsFragment_to_settingsFragment)
                }
            }

            binding.drawerLayout.closeDrawer(binding.navViewNews)

            true
        }
    }

    private fun showError(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG).show()
    }


    private fun navigateToNewsDetails(article: Article) {
        val action = NewsFragmentDirections.actionNewsFragmentToNewsDetailsFragment(
            article.title,
            article.source.name,
            article.description,
            article.url,
            article.urlToImage ?: "",
            article.publishedAt
        )
        findNavController().navigate(action)
    }


}