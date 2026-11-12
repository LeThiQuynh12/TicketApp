package com.example.musicapp.presentation.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.content.Context
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import com.example.musicapp.R
import com.example.musicapp.databinding.FragmentSearchBinding

class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private val recentSearches = mutableListOf(
        "Concert Hà Nội",
        "Nhạc hội mùa hè",
        "Sự kiện cuối tuần"
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupSearch()
        setupPopularKeywords()
        setupCategories()
        setupRecentSearches()
        setupClearHistory()
    }

    private fun setupSearch() {
        binding.etSearch.addTextChangedListener { text ->
            val keyword = text?.toString()?.trim().orEmpty()

            binding.btnClearSearch.visibility =
                if (keyword.isNotEmpty()) View.VISIBLE else View.GONE

            if (keyword.isEmpty()) {
                binding.layoutExplore.visibility = View.VISIBLE
                binding.layoutSearchResults.visibility = View.GONE
            } else {
                binding.layoutExplore.visibility = View.GONE
                binding.layoutSearchResults.visibility = View.VISIBLE

                binding.tvSearchResultTitle.text =
                    "Kết quả cho \"$keyword\""

                // Chưa kết nối API nên hiển thị trạng thái chờ dữ liệu.
                binding.rvSearchResults.visibility = View.GONE
                binding.layoutNoSearchResult.visibility = View.VISIBLE
            }
        }

        binding.btnClearSearch.setOnClickListener {
            binding.etSearch.text?.clear()
            binding.etSearch.requestFocus()
            showKeyboard()
        }

        binding.etSearch.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                val keyword = binding.etSearch.text.toString().trim()

                if (keyword.isNotEmpty()) {
                    addRecentSearch(keyword)
                }

                hideKeyboard()
                true
            } else {
                false
            }
        }
    }

    private fun setupPopularKeywords() {
        val keywordViews = listOf(
            binding.keywordConcert,
            binding.keywordEDM,
            binding.keywordAcoustic,
            binding.keywordFootball,
            binding.keywordWorkshop
        )

        keywordViews.forEach { textView ->
            textView.setOnClickListener {
                val keyword = textView.text.toString()
                    .removePrefix("#")
                    .trim()

                binding.etSearch.setText(keyword)
                binding.etSearch.setSelection(keyword.length)
            }
        }
    }

    private fun setupCategories() {
        val categories = listOf(
            binding.chipMusic,
            binding.chipSport,
            binding.chipWorkshop,
            binding.chipFestival,
            binding.chipOther
        )

        categories.forEach { chip ->
            chip.setOnClickListener {
                categories.forEach { item ->
                    item.setBackgroundResource(R.drawable.bg_search_chip)
                    item.setTextColor(
                        requireContext().getColor(R.color.search_chip_text)
                    )
                }

                chip.setBackgroundResource(
                    R.drawable.bg_search_chip_selected
                )
                chip.setTextColor(
                    requireContext().getColor(R.color.search_chip_selected_text)
                )

                val category = chip.text.toString()
                    .substringAfter(" ")
                    .trim()

                binding.etSearch.setText(category)
                binding.etSearch.setSelection(category.length)
            }
        }
    }

    private fun setupRecentSearches() {
        val recentViews = listOf(
            binding.recentSearch1,
            binding.recentSearch2,
            binding.recentSearch3
        )

        recentViews.forEach { textView ->
            textView.setOnClickListener {
                val keyword = textView.text.toString()
                    .substringAfter("◷")
                    .trim()

                binding.etSearch.setText(keyword)
                binding.etSearch.setSelection(keyword.length)
            }
        }
    }

    private fun setupClearHistory() {
        binding.btnClearHistory.setOnClickListener {
            recentSearches.clear()
            binding.layoutRecentSearches.visibility = View.GONE
            binding.btnClearHistory.visibility = View.GONE
        }
    }

    private fun addRecentSearch(keyword: String) {
        recentSearches.remove(keyword)
        recentSearches.add(0, keyword)

        // TODO: Cập nhật danh sách lịch sử lên giao diện
        // và lưu bằng SharedPreferences hoặc DataStore.
    }

    private fun showKeyboard() {
        val inputMethodManager = requireContext()
            .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        inputMethodManager.showSoftInput(
            binding.etSearch,
            InputMethodManager.SHOW_IMPLICIT
        )
    }

    private fun hideKeyboard() {
        val inputMethodManager = requireContext()
            .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        inputMethodManager.hideSoftInputFromWindow(
            binding.etSearch.windowToken,
            0
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
