package com.example.githubfirebaseissue.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint


abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutRes())
    }

    fun replaceFragment(
        container: Int,
        fragment: Fragment,
        tag: String,
        addToBackStack: Boolean = false
    ) {

        if (addToBackStack) {
            supportFragmentManager.beginTransaction()
                .replace(container, fragment, tag)
                .addToBackStack(tag)
                .commit()
        } else {
            supportFragmentManager.beginTransaction()
                .replace(container, fragment, tag)
                .commit()
        }

    }

    abstract fun getLayoutRes(): Int
}