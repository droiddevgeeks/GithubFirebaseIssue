package com.example.githubfirebaseissue.usecase

import com.example.githubfirebaseissue.model.Comment
import com.example.githubfirebaseissue.model.Issue
import com.example.githubfirebaseissue.repository.GithubRepositoryImpl
import com.nhaarman.mockitokotlin2.mock
import io.reactivex.Single
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.context
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import org.junit.Assert.assertEquals
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith
import org.mockito.Mockito.*

@RunWith(JUnitPlatform::class)
class GetFireBaseIssueUseCaseImplTestSpek : Spek({

    lateinit var useCase: GetFireBaseIssueUseCase
    lateinit var repo: GithubRepositoryImpl

    describe("GetFireBase Issue UseCase Impl Test Spek") {
        describe("When firebase ios issues fetch api called") {
            beforeEachTest {
                repo = mock()
                useCase = GetFireBaseIssueUseCaseImpl(repo)
            }

            afterEachTest {
                reset(repo)
            }
            context("When firebase ios issues fetch api successful") {
                it("Api return list successfully") {
                    val issueResponse = mock(Issue::class.java)
                    val issueList = mutableListOf<Issue>()
                    issueList.add(issueResponse)
                    `when`(repo.getFireBaseIosIssues()).thenReturn(Single.just(issueList.toList()))

                    useCase.getFireBaseIosIssues()
                        .test()
                        .assertComplete()
                        .assertValueCount(1)
                        .assertValue {
                            assertEquals(issueList, it)
                            true
                        }

                    verify(repo).getFireBaseIosIssues()
                }
            }
        }

        describe("When firebase user comment api called") {
            beforeEachTest {
                repo = mock()
                useCase = GetFireBaseIssueUseCaseImpl(repo)
            }
            context("When firebase user comment api successful") {
                it("Api return list of comments successfully") {
                    val commentResponse = mock(Comment::class.java)
                    val commentList = mutableListOf<Comment>()
                    commentList.add(commentResponse)

                    val number = 1234
                    `when`(repo.getFireBaseIosUserComments(number)).thenReturn(Single.just(commentList.toList()))

                    useCase.getFireBaseIssuesComments(number)
                        .test()
                        .assertComplete()
                        .assertValueCount(1)
                        .assertValue {
                            assertEquals(commentList, it)
                            true
                        }

                    verify(repo).getFireBaseIosUserComments(number)
                    verifyNoMoreInteractions(repo)
                }
            }
        }
    }
})