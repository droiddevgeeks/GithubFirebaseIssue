package com.example.githubfirebaseissue.ui.fragment

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubfirebaseissue.R
import com.example.githubfirebaseissue.callback.IAdapterCallback
import com.example.githubfirebaseissue.callback.IFragmentChangeCallback
import com.example.githubfirebaseissue.model.Issue
import com.example.githubfirebaseissue.ui.adapter.IssueAdapter
import kotlinx.android.synthetic.main.issue_layout.*

class IssuesFragment : AbstractIssueFragment(), IAdapterCallback {

    companion object {
        val TAG = IssuesFragment::class.java.name
        fun newInstance(): IssuesFragment {
            return IssuesFragment()
        }
    }

    private lateinit var fragmentChangeListener: IFragmentChangeCallback
    private val issueData by lazy { ArrayList<Issue>() }
    private val issueAdapter by lazy {
        IssueAdapter(issueData, this)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        fragmentChangeListener = context as IFragmentChangeCallback
    }

    override fun getLayoutRes() = R.layout.issue_layout

    override fun viewInitialization(view: View) {
        super.viewInitialization(view)
        initAdapter()
    }

    private fun initAdapter() {
        with(parent_recycler) {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = issueAdapter
        }
    }

    override fun setIssuesData(list: List<Issue>) {
        issueData.addAll(list)
        with(issueAdapter) {
            notifyDataSetChanged()
        }
    }

    override fun showLoadingState(loading: Boolean) {
        if (loading)
            shimmer_view_container.startShimmerAnimation()
        else {
            shimmer_view_container.stopShimmerAnimation()
            shimmer_view_container.visibility = View.GONE
        }
    }

    override fun onError(message: String) {
        showToast(message)
    }


    override fun onIssueClick(number: Int) {
        fragmentChangeListener.onFragmentChange(
            CommentsFragment.getInstance(number),
            CommentsFragment.TAG
        )
    }

}