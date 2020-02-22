package com.example.githubfirebaseissue.model

import com.google.gson.annotations.SerializedName

data class Issue(
    @SerializedName("title") val title: String,
    @SerializedName("body") val body: String,
    @SerializedName("comments_url") val commentsUrl: String,
    @SerializedName("number") val number: Int,
    @SerializedName("updated_at") val updatedAt: String,
    @SerializedName("user") val user: User
)

data class User(@SerializedName("login") val userName: String)