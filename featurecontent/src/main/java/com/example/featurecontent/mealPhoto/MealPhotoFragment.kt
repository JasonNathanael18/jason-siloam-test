package com.example.featurecontent.mealPhoto

import com.example.commonui.BaseFragment
import com.example.featurecontent.R
import com.example.featurecontent.databinding.FragmentMealPhotoBinding
import dagger.hilt.android.AndroidEntryPoint

import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions

@AndroidEntryPoint
class MealPhotoFragment : BaseFragment(R.layout.fragment_meal_photo) {

    private val mealPhotoFragmentArgs: MealPhotoFragmentArgs by navArgs()

    private lateinit var binding: FragmentMealPhotoBinding

    override fun initComponent() {
        super.initComponent()
        binding = FragmentMealPhotoBinding.bind(requireView())

        val glideOpt = RequestOptions().diskCacheStrategy(DiskCacheStrategy.RESOURCE).fitCenter()
            .placeholder(com.example.commonui.R.drawable.ic_launcher_background)
            .error(com.example.commonui.R.drawable.ic_launcher_background)
        Glide.with(this)
            .load(mealPhotoFragmentArgs.mealPhotoUrl)
            .apply(glideOpt)
            .thumbnail(0.1f)
            .into(binding.preview)
    }
}