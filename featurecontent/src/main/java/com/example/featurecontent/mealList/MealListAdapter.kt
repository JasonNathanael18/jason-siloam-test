package com.example.featurecontent.mealList

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.domain.model.Meal
import com.example.featurecontent.R
import com.example.featurecontent.databinding.CompInfoItemMealListBinding
import dagger.hilt.android.qualifiers.ActivityContext
import javax.inject.Inject

class MealListAdapter @Inject constructor(@ActivityContext private val context: Context) :
    RecyclerView.Adapter<MealListAdapter.MovieListViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(view: View, item: Meal)
        fun onImageClick(view: View, item: Meal)
    }

    inner class MovieListViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = CompInfoItemMealListBinding.bind(view)
    }

    private var onItemClickListener: OnItemClickListener? = null
    var datas = ArrayList<Meal>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieListViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.comp_info_item_meal_list,
            parent, false
        )
        return MovieListViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MovieListViewHolder, position: Int) {
        val data = datas[position]
        val glideOpt = RequestOptions().diskCacheStrategy(DiskCacheStrategy.RESOURCE).fitCenter()
            .placeholder(com.example.commonui.R.drawable.ic_launcher_background)
            .error(com.example.commonui.R.drawable.ic_launcher_background)
        Glide.with(context)
            .load(data.strMealThumb)
            .apply(glideOpt)
            .thumbnail(0.1f)
            .into(holder.binding.ivThumbnail)
        holder.binding.apply {
            labelMealName.text = data.strMeal
            labelMealCategory.text = data.strCategory
            labelMealArea.text = data.strArea
            labelMealTags.text = data.strTags
        }

        holder.binding.ivThumbnail.setOnClickListener {
            onItemClickListener?.onImageClick(it, datas[position])
        }

        holder.binding.itemUser.setOnClickListener {
            onItemClickListener?.onItemClick(it, datas[position])
        }
    }

    override fun getItemCount(): Int {
        return datas.size
    }

    fun reloadData(paramDatas: List<Meal>) {
        datas.clear()
        datas.addAll(paramDatas)
        notifyDataSetChanged()
    }

    fun addData(newData: List<Meal>) {
        val previousCount = datas.size
        datas.addAll(newData)
        notifyItemRangeInserted(previousCount, datas.size)
    }

    fun clearData() {
        datas.clear()
        notifyDataSetChanged()
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        onItemClickListener = listener
    }
}