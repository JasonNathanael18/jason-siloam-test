package com.example.featurecontent.mealList

import android.content.Context
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.commonui.BaseFragment
import com.example.featurecontent.R
import com.example.featurecontent.databinding.FragmentMealListBinding
import com.example.navigation.DataConvert
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MealListFragment : BaseFragment(R.layout.fragment_meal_list) {

    @Inject
    lateinit var adapter: MealListAdapter

    @Inject
    lateinit var dataConvert: DataConvert

    private lateinit var binding: FragmentMealListBinding
    private val viewModel: MealListViewModel by viewModels()

    override fun initComponent() {
        super.initComponent()
        binding = FragmentMealListBinding.bind(requireView())

        val layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rvMovieList.setLayoutManager(layoutManager)
        binding.rvMovieList.setAdapter(adapter)
        binding.rvMovieList.initialHideList()

        viewModel.getMealList()
    }

    override fun initEventListener() {
        super.initEventListener()

        binding.compSearchBox.onSearchPerformed { query ->
            hideKeyboard()
            adapter.clearData()
            binding.rvMovieList.showWait()
            viewModel.requestSearch(query)
        }
    }

    override fun initObserver() {
        super.initObserver()
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect {
                    when {
                        it.isLoading -> {
                            Log.d("MainActivity", "Loading")
                            binding.rvMovieList.showWait()
                        }

                        it is MealUiState.HasMealList -> {
                            Log.d("MainActivity", "Sukses")

                            adapter.addData(it.mealList)
                            binding.rvMovieList.apply {
                                hideWait()
                                showData()
                            }
                        }

                        it is MealUiState.MealListEmpty -> {
                            Log.d("MainActivity", "Kosong")
                            if (it.error.isNotEmpty()) {
                                binding.rvMovieList.apply {
                                    hideWait()
                                    showEmpty("No Data Found")
                                }
                                Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT)
                                    .show()
                            } else {
                                binding.rvMovieList.apply {
                                    if (!it.isLoading) {
                                        hideWait()
                                        if (!it.isPreviousPageLoaded) showEmpty("No Data Found")
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }


    private fun hideKeyboard() {
        val imm: InputMethodManager =
            activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view?.windowToken, 0)
    }
}