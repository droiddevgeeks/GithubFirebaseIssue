package com.example.githubfirebaseissue.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubfirebaseissue.R
import com.example.githubfirebaseissue.model.Comment
import com.example.githubfirebaseissue.ui.adapter.CommentAdapter
import kotlinx.android.synthetic.main.comments_layout.*

class CommentsFragment : AbstractCommentsFragment() {

    companion object {
        val TAG = CommentsFragment::class.java.name
        fun getInstance(number: Int): CommentsFragment {
            val fragment = CommentsFragment()
            val arg = Bundle()
            arg.putInt(ISSUE_NUMBER, number)
            fragment.arguments = arg
            return fragment
        }
    }

    private val commentList by lazy { ArrayList<Comment>() }
    private val issueAdapter by lazy {
        CommentAdapter(commentList)
    }


    override fun getLayoutRes() = R.layout.comments_layout

    override fun viewInitialization(view: View) {
        super.viewInitialization(view)
        initAdapter()
    }

    private fun initAdapter() {
        with(comments_recycler) {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = issueAdapter
        }
    }

    override fun setCommentsData(list: List<Comment>) {
        commentList.addAll(list)
        with(issueAdapter) {
            notifyDataSetChanged()
        }
    }

    override fun showNoCommentDialog() {
        showErrorDialog(getString(R.string.no_comment), getString(R.string.go_back))
    }


    override fun showLoadingState(loading: Boolean) {
        if (loading)
            progress.visibility = View.VISIBLE
        else
            progress.visibility = View.GONE
    }

    override fun onError(message: String) {
        showToast(message)
    }

}