package com.example.githubfirebaseissue.usecase


import com.example.githubfirebaseissue.model.Comment
import com.example.githubfirebaseissue.model.Issue
import com.example.githubfirebaseissue.repository.GithubRepository
import io.reactivex.Single
import javax.inject.Inject

interface GetFireBaseIssueUseCase {
    fun getFireBaseIosIssues(): Single<List<Issue>>
    fun getFireBaseIssuesComments(number: Int): Single<List<Comment>>
}

class GetFireBaseIssueUseCaseImpl @Inject constructor(private val githubRepository: GithubRepository) :
    GetFireBaseIssueUseCase {

    override fun getFireBaseIosIssues(): Single<List<Issue>> =
        githubRepository.getFireBaseIosIssues()

    override fun getFireBaseIssuesComments(number: Int): Single<List<Comment>> =
        githubRepository.getFireBaseIosUserComments(number)
}