package com.example.githubfirebaseissue.api

import com.example.githubfirebaseissue.model.Comment
import com.example.githubfirebaseissue.model.Issue
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface GithubApi {

    @GET("issues")
    fun getFireBaseIosIssues(): Single<List<Issue>>


    @GET("issues/{number}/comments")
    fun getFireBaseIosUserComments(@Path("number") number: Int): Single<List<Comment>>

}