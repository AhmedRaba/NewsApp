package com.training.newsapp.ui.frags

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.training.newsapp.R
import com.training.newsapp.data.LanguagePreferences
import com.training.newsapp.databinding.FragmentSettingsBinding
import kotlinx.coroutines.launch
import java.util.Locale


class SettingsFragment : Fragment() {


    private lateinit var binding: FragmentSettingsBinding
    private lateinit var languagePreferences: LanguagePreferences
    private var isInitialSetup = true
    private var currentLanguageCode: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentSettingsBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        languagePreferences = LanguagePreferences(requireContext())
        setupDrawer()
        setupSpinner()
        observeSelectedLanguage()
    }


    private fun setupDrawer() {
        binding.ivDrawerSettings.setOnClickListener {
            binding.drawerLayoutSettings.openDrawer(binding.navViewSettings)
        }

        val menu = binding.navViewSettings.menu
        menu.removeItem(R.id.menu_settings)


        binding.navViewSettings.setNavigationItemSelectedListener { menuItem ->

            when (menuItem.itemId) {
                R.id.menu_categories -> {
                    findNavController().popBackStack()
                }
            }
            binding.drawerLayoutSettings.closeDrawer(binding.navViewSettings)
            true
        }
    }

    private fun setupSpinner() {
        val languages = arrayOf("English", "العربية")
        val adapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, languages)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        binding.spinnerLanguage.adapter = adapter
        binding.spinnerLanguage.setSelection(if (Locale.getDefault().language == "en") 0 else 1)

        binding.spinnerLanguage.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long,
                ) {
                    if (!isInitialSetup) {
                        val selectedLanguageCode = if (position == 0) "en" else "ar"

                        if (selectedLanguageCode != currentLanguageCode) {
                            saveLanguage(selectedLanguageCode)
                        }
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                }
            }
    }

    private fun saveLanguage(languageCode: String) {
        lifecycleScope.launch {
            languagePreferences.saveLanguage(languageCode)
            setLocale(languageCode)
        }
    }

    private fun observeSelectedLanguage() {

        lifecycleScope.launch {
            languagePreferences.selectedLanguage.collect { languageCode ->
                languageCode?.let {
                    currentLanguageCode = it
                    val position = if (it == "en") 0 else 1
                    isInitialSetup = true
                    binding.spinnerLanguage.setSelection(position)
                    isInitialSetup = false
                }
            }
        }
    }


    private fun setLocale(languageCode: String) {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)

        val config = resources.configuration
        config.setLocale(locale)

        requireActivity().createConfigurationContext(config)

        val intent = requireActivity().intent
        requireActivity().finish()

        startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))


    }


}