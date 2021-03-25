package com.example.githubfirebaseissue.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.example.githubfirebaseissue.base.BaseFragment
import com.example.githubfirebaseissue.common.EventObserver
import com.example.githubfirebaseissue.model.Issue
import com.example.githubfirebaseissue.ui.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
abstract class AbstractIssueFragment : BaseFragment() {

    private val viewModel: MainViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.fetchFireBaseIosIssueList()
    }

    override fun viewInitialization(view: View) {
        observeDataChange()
    }


    private fun observeDataChange() {
        viewModel.loadingState.observe(viewLifecycleOwner, { showLoadingState(it) })
        viewModel.apiError.observe(viewLifecycleOwner, EventObserver { handleError(it) })
        viewModel.issueLiveData.observe(viewLifecycleOwner, EventObserver {
            setIssuesData(it)
        })
    }


    abstract fun setIssuesData(list: List<Issue>)

}