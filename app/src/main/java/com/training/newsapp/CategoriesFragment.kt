package com.training.newsapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.training.newsapp.databinding.FragmentCategoriesBinding

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
        binding.btnScience.setOnClickListener {
            navigateToNewsFrag("science")
        }
        binding.btnPolitics.setOnClickListener {
            navigateToNewsFrag("general")
        }
        binding.btnEnvironment.setOnClickListener {
            navigateToNewsFrag("environment")
        }


    }

    private fun navigateToNewsFrag(category: String) {
        val action = CategoriesFragmentDirections.actionCategoriesFragmentToNewsFragment(category)
        findNavController().navigate(action)

    }


}