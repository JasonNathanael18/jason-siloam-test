package com.example.featurecontent.mealDetail

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.commonui.BaseFragment
import com.example.domain.model.Meal
import com.example.featurecontent.R
import com.example.featurecontent.databinding.FragmentMealDetailBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MealDetailFragment : BaseFragment(R.layout.fragment_meal_detail) {

    private val mealDetailFragmentArgs: MealDetailFragmentArgs by navArgs()

    private lateinit var binding: FragmentMealDetailBinding

    private val viewModel: MealDetailViewModel by viewModels()

    override fun initComponent() {
        super.initComponent()
        binding = FragmentMealDetailBinding.bind(requireView())

        //Toast.makeText(requireContext(), mealDetailFragmentArgs.mealId, Toast.LENGTH_SHORT).show()
        viewModel.getMealList(mealDetailFragmentArgs.mealId)
    }

    override fun initObserver() {
        super.initObserver()
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect {
                    when {
                        it.isLoading -> {
                        }

                        it is MealDetailUiState.HasMealDetail -> {
                            setUi(it.mealList.first())
                        }

                        it is MealDetailUiState.MealDetailEmpty -> {
                            if (it.error.isNotEmpty()) {

                            } else {
                                if (!it.isLoading) {
                                    //showEmpty("No Data Found")
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun setUi(data: Meal) {
        val glideOpt = RequestOptions().diskCacheStrategy(DiskCacheStrategy.RESOURCE).fitCenter()
            .placeholder(com.example.commonui.R.drawable.ic_launcher_background)
            .error(com.example.commonui.R.drawable.ic_launcher_background)
        Glide.with(this)
            .load(data.strMealThumb)
            .apply(glideOpt)
            .thumbnail(0.1f)
            .into(binding.ivThumbnail)
        binding.apply {
            labelMealName.text = data.strMeal
            labelMealCategory.text = data.strCategory
            labelMealArea.text = data.strArea
            labelMealTags.text = data.strTags
        }
    }
}