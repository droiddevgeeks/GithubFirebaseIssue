package com.example.githubfirebaseissue.repository

import com.example.githubfirebaseissue.model.Comment
import com.example.githubfirebaseissue.model.Issue
import io.reactivex.Single

interface GithubRepository {
    fun getFireBaseIosIssues(): Single<List<Issue>>
    fun getFireBaseIosUserComments(number: Int): Single<List<Comment>>
}