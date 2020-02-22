package com.example.githubfirebaseissue.model

import com.google.gson.annotations.SerializedName

data class Comment(
    @SerializedName("user") val user: User,
    @SerializedName("body") val body: String,
    @SerializedName("updated_at") val updatedAt: String
)