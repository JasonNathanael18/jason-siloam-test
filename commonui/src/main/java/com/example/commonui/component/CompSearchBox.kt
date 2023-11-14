package com.example.commonui.component

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.inputmethod.EditorInfo
import android.widget.FrameLayout
import com.example.commonui.databinding.CompSearchBoxBinding

class CompSearchBox(context: Context, attrs: AttributeSet) : FrameLayout(context, attrs) {

    private var binding: CompSearchBoxBinding
    private var mContext: Context

    private var callback: ((String) -> Unit)? = null

    init {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding = CompSearchBoxBinding.inflate(inflater)
        mContext = context
        binding.etSearch.setOnEditorActionListener { v, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                performSearch()
            }
            true
        }

        addView(binding.root)
    }

    private fun performSearch() {
        binding.etSearch.clearFocus()
        callback?.invoke(binding.etSearch.text.toString())
    }

    fun onSearchPerformed(callback: ((String) -> Unit)) {
        this.callback = callback
    }
}