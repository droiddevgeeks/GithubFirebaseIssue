package com.example.githubfirebaseissue.ui.activity

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.githubfirebaseissue.R
import com.example.githubfirebaseissue.base.BaseActivity
import com.example.githubfirebaseissue.callback.IFragmentChangeCallback
import com.example.githubfirebaseissue.ui.fragment.IssuesFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity(), IFragmentChangeCallback {

    override fun getLayoutRes() = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        openIssueFragment()
    }

    private fun openIssueFragment() {
        replaceFragment(R.id.container, IssuesFragment.newInstance(), IssuesFragment.TAG)
    }


    override fun onFragmentChange(fragment: Fragment, tag: String) {
        replaceFragment(R.id.container, fragment, tag, true)
    }
}
