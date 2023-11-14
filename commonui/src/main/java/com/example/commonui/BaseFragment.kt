package com.example.commonui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController

open class BaseFragment(layoutId: Int) : Fragment(layoutId) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initComponent()
        initEventListener()
        initObserver()
        loadData()
    }

    protected fun navigate(id: NavDirections) {
        findNavController().navigate(id)
    }

    protected open fun initComponent(){
        Log.d(TAG, "init component")
    }

    protected open fun initEventListener(){
        Log.d(TAG, "init eventlistener")
    }

    protected open fun initObserver(){
        Log.d(TAG, "init observer")
    }

    protected open fun loadData(){
        Log.d(TAG, "init loaddata")
    }

    companion object{
        const val TAG = "TAG"
    }

}