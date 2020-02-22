package com.example.githubfirebaseissue.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.githubfirebaseissue.R
import com.example.githubfirebaseissue.callback.IAdapterCallback
import com.example.githubfirebaseissue.model.Issue
import kotlinx.android.synthetic.main.issue_row_item.view.*

class IssueAdapter(private val items: List<Issue>, private val listener: IAdapterCallback) :
    RecyclerView.Adapter<IssueAdapter.IssueViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = IssueViewHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.issue_row_item, parent, false
        )
    )

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: IssueViewHolder, position: Int) {
        holder.bind(items[position])
    }

    inner class IssueViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

        fun bind(issue: Issue) {
            with(view) {
                txt_issue_title.text = issue.title
                txt_issue.text = issue.body
            }

            view.setOnClickListener {
                listener.onIssueClick(issue.number)
            }
        }
    }
}