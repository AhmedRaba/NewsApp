package com.training.newsapp.ui.frags

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.training.newsapp.R
import com.training.newsapp.data.api.model.Article
import com.training.newsapp.databinding.FragmentCategoriesBinding
import com.training.newsapp.ui.frags.CategoriesFragmentDirections

class CategoriesFragment : Fragment() {

    private lateinit var binding: FragmentCategoriesBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentCategoriesBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupCategories()
        setupDrawer()
    }


    private fun setupCategories() {

        binding.btnBusiness.setOnClickListener {
            navigateToNewsFrag("business")
        }
        binding.btnSports.setOnClickListener {
            navigateToNewsFrag("sports")
        }
        binding.btnHealth.setOnClickListener {
            navigateToNewsFrag("health")
        }
        binding.btnTechnology.setOnClickListener {
            navigateToNewsFrag("technology")
        }
        binding.btnPolitics.setOnClickListener {
            navigateToNewsFrag("general")
        }
        binding.btnEnvironment.setOnClickListener {
            navigateToNewsFrag("environment")
        }


    }

    private fun setupDrawer() {
        binding.ivDrawerCategories.setOnClickListener {
            binding.drawerLayoutCategories.openDrawer(binding.navViewCategories)
        }

        val menu = binding.navViewCategories.menu
        menu.removeItem(R.id.menu_categories)

        binding.navViewCategories.setNavigationItemSelectedListener { menuItem ->

            when (menuItem.itemId) {
                R.id.menu_settings -> {
                    findNavController().navigate(R.id.action_categoriesFragment_to_settingsFragment)
                }
            }
            binding.drawerLayoutCategories.closeDrawer(binding.navViewCategories)

            true
        }


    }

    private fun navigateToNewsFrag(category: String) {

        val action =
            CategoriesFragmentDirections.actionCategoriesFragmentToNewsFragment(
                category
            )
        findNavController().navigate(action)

    }


}