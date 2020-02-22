package com.example.githubfirebaseissue.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.githubfirebaseissue.base.BaseFragment
import com.example.githubfirebaseissue.base.CustomViewModelFactory
import com.example.githubfirebaseissue.common.EventObserver
import com.example.githubfirebaseissue.model.Comment
import com.example.githubfirebaseissue.ui.viewmodel.MainViewModel
import javax.inject.Inject

abstract class AbstractCommentsFragment : BaseFragment() {

    companion object {
        const val ISSUE_NUMBER = "ISSUE_NUMBER"
    }

    @Inject
    lateinit var viewModelFactory: CustomViewModelFactory

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
    }


    override fun viewInitialization(view: View) {
        getIssueNumber()?.let {
            viewModel.fetchIssueComments(it)
            observeDataChange()
        }
    }

    private fun getIssueNumber() = arguments?.getInt(ISSUE_NUMBER)


    private fun observeDataChange() {
        viewModel.loadingState.observe(viewLifecycleOwner, Observer { showLoadingState(it) })
        viewModel.apiError.observe(viewLifecycleOwner, EventObserver { handleError(it) })
        viewModel.commentsLiveData.observe(viewLifecycleOwner, EventObserver {
            if (it.isNotEmpty())
                setCommentsData(it)
            else
                showNoCommentDialog()
        })
    }

    abstract fun setCommentsData(list: List<Comment>)
    abstract fun showNoCommentDialog()
}