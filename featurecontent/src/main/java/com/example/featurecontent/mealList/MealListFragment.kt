package com.example.featurecontent.mealList

import android.content.Context
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.commonui.BaseFragment
import com.example.domain.SharedPreferenceModule
import com.example.domain.model.Meal
import com.example.featurecontent.R
import com.example.featurecontent.databinding.FragmentMealListBinding
import com.example.navigation.DataConvert
import com.example.navigation.NavigationFlow
import com.example.navigation.ToFlowNavigatable
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MealListFragment : BaseFragment(R.layout.fragment_meal_list),
    MealListAdapter.OnItemClickListener {

    @Inject
    lateinit var adapter: MealListAdapter

    @Inject
    lateinit var dataConvert: DataConvert

    @Inject
    lateinit var pref: SharedPreferenceModule

    private lateinit var binding: FragmentMealListBinding
    private val viewModel: MealListViewModel by viewModels()

    override fun initComponent() {
        super.initComponent()
        val menuHost: MenuHost = requireActivity()

        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.logout -> {
                        pref.setLoginStatus(isLogin = false)
                        (requireActivity() as ToFlowNavigatable).navigateToFlow(NavigationFlow.AuthenticationFlow)
                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)

        binding = FragmentMealListBinding.bind(requireView())
        adapter.setOnItemClickListener(this)

        val layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rvMovieList.setLayoutManager(layoutManager)
        binding.rvMovieList.setAdapter(adapter)
        binding.rvMovieList.initialHideList()

        adapter.clearData()
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
                             binding.rvMovieList.showWait()
                        }

                        it is MealUiState.HasMealList -> {
                            adapter.addData(it.mealList)
                            binding.rvMovieList.apply {
                                hideWait()
                                showData()
                            }
                        }

                        it is MealUiState.MealListEmpty -> {
                             if (it.error.isNotEmpty()) {
                                binding.rvMovieList.apply {
                                    hideWait()
                                    showEmpty("No Data Found")
                                }
                            } else {
                                binding.rvMovieList.apply {
                                    if (!it.isLoading) {
                                        hideWait()
                                        showEmpty("No Data Found")
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

    override fun onItemClick(view: View, item: Meal) {
        findNavController().navigate(
            MealListFragmentDirections.actionMealListFragmentToMealDetailFragment(
                item.idMeal
            )
        )
    }

    override fun onImageClick(view: View, item: Meal) {
        findNavController().navigate(
            MealListFragmentDirections.actionMealListFragmentToMealPhotoFragment(
                item.strMealThumb
            )
        )
    }

}