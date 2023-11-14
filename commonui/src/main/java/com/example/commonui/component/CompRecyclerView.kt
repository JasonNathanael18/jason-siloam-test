package com.example.commonui.component

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.commonui.databinding.CompRecyclerViewBinding

class CompRecyclerView (context: Context, attrs: AttributeSet) : FrameLayout(context,attrs) {

    private val binding: CompRecyclerViewBinding
    private var isLoadingMore = true
    private var visibleCount: Int = 0
    private var totalItemCount: Int = 0
    private var pastVisibleItems : Int = 0

    interface LoadMoreListener {
        fun onMoreRequest()
    }
    var listener: LoadMoreListener? = null

    init {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding = CompRecyclerViewBinding.inflate(inflater)
        binding.wait.visibility = GONE
        binding.waitMore.visibility = GONE
        binding.emptyView.visibility = GONE
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.addItemDecoration(DividerItemDecoration(this.context, DividerItemDecoration.VERTICAL))

        binding.recyclerView.clipToPadding = false
        binding.recyclerView.setPadding(0, 0, 0, 32)
        binding.recyclerView.addOnScrollListener(object: RecyclerView.OnScrollListener(){
            override fun onScrolled(rv: RecyclerView, dx: Int, dy: Int) {
                if ( dy > 0) {
                    val layoutManager = rv.layoutManager
                    if ( layoutManager is LinearLayoutManager) {
                        visibleCount = layoutManager.childCount
                        totalItemCount = layoutManager.itemCount
                        pastVisibleItems = layoutManager.findFirstVisibleItemPosition()
                        if ( isLoadingMore && (visibleCount+pastVisibleItems) >= totalItemCount ){
                            isLoadingMore = false
                            if (listener != null) {
                                binding.waitMore.visibility = VISIBLE
                                listener!!.onMoreRequest()
                            }
                        }
                    }
                }
            }
        })
        addView(binding.root)
    }

    fun showWait() {
        binding.wait.visibility = VISIBLE
    }

    fun hideWait() {
        binding.wait.visibility = GONE
        if(listener != null)
        {
            binding.waitMore.visibility = GONE
            isLoadingMore = true
        }
    }

    fun showEmpty(msg: String) {
        binding.labelErrorMsg.text = msg
        binding.emptyView.visibility = VISIBLE
        binding.recyclerView.visibility = INVISIBLE
    }

    fun showData() {
        binding.recyclerView.visibility = VISIBLE
        binding.emptyView.visibility = GONE
    }

    fun initialHideList() {
        binding.recyclerView.visibility = GONE
        binding.emptyView.visibility = GONE
    }

    fun setLayoutManager(layout: RecyclerView.LayoutManager) {
        binding.recyclerView.layoutManager = layout
    }

    fun setAdapter(adapter: RecyclerView.Adapter<*>) {
        binding.recyclerView.adapter = adapter
    }

}