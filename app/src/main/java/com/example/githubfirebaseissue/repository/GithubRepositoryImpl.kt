package com.example.githubfirebaseissue.repository

import com.example.githubfirebaseissue.api.GithubApi
import com.example.githubfirebaseissue.model.Comment
import com.example.githubfirebaseissue.model.Issue
import io.reactivex.Single
import javax.inject.Inject

class GithubRepositoryImpl @Inject constructor(private val githubApi: GithubApi) :
    GithubRepository {

    override fun getFireBaseIosIssues(): Single<List<Issue>> {
        return githubApi.getFireBaseIosIssues()
    }

    override fun getFireBaseIosUserComments(number: Int): Single<List<Comment>> {
        return githubApi.getFireBaseIosUserComments(number)
    }
}