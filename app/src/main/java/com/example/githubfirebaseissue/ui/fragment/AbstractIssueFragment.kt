package com.example.githubfirebaseissue.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.githubfirebaseissue.base.BaseFragment
import com.example.githubfirebaseissue.base.CustomViewModelFactory
import com.example.githubfirebaseissue.common.EventObserver
import com.example.githubfirebaseissue.helper.ApplicationUtil
import com.example.githubfirebaseissue.model.Issue
import com.example.githubfirebaseissue.ui.viewmodel.MainViewModel
import javax.inject.Inject

abstract class AbstractIssueFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: CustomViewModelFactory

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.fetchFireBaseIosIssueList()
    }

    override fun viewInitialization(view: View) {
        observeDataChange()
    }


    private fun observeDataChange() {
        viewModel.loadingState.observe(viewLifecycleOwner, Observer { showLoadingState(it) })
        viewModel.apiError.observe(viewLifecycleOwner, EventObserver { handleError(it) })
        viewModel.issueLiveData.observe(viewLifecycleOwner, EventObserver {
            setIssuesData(it)
        })
    }


    abstract fun setIssuesData(list: List<Issue>)

}