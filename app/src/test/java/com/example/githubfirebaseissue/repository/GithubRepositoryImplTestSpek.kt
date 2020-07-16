package com.example.githubfirebaseissue.repository

import com.example.githubfirebaseissue.api.GithubApi
import com.example.githubfirebaseissue.model.Comment
import com.example.githubfirebaseissue.model.Issue
import com.nhaarman.mockitokotlin2.mock
import io.reactivex.Single
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.it
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.Mockito.*
import retrofit2.HttpException
import retrofit2.Response

@RunWith(JUnitPlatform::class)
class GithubRepositoryImplTestSpek : Spek({

    lateinit var endpoint: GithubApi

    group("Github Repository Impl Test using Spek") {
        group("When firebase ios issues fetch api called")
        {
            beforeEachTest {
                endpoint = mock()
            }
            test("When firebase ios issues fetch api successful") {
                it("Should return list of issues") {
                    val expectedResponse = listOf(Mockito.mock(Issue::class.java))
                    `when`(endpoint.getFireBaseIosIssues()).thenReturn(Single.create {
                        it.onSuccess(
                            expectedResponse
                        )
                    })
                    endpoint.getFireBaseIosIssues()
                        .test()
                        .assertComplete()
                        .assertNoErrors()
                        .assertValue(expectedResponse)

                    verify(endpoint).getFireBaseIosIssues()
                }
            }

            test("When firebase ios issues fetch api failed") {
                it("Should return error") {
                    val body = Response.error<Issue>(
                        400,
                        "{\"key\":[\"something went wrong\"]}".toResponseBody("application/json".toMediaTypeOrNull())
                    )
                    val exception = HttpException(body)

                    `when`(endpoint.getFireBaseIosIssues()).thenReturn(Single.error(exception))
                    endpoint.getFireBaseIosIssues()
                        .test()
                        .assertValueCount(0)
                        .assertError { it is HttpException }

                    verify(endpoint).getFireBaseIosIssues()
                }
            }
        }

        group("When firebase ios user comment api called") {
            test("When firebase ios user comment api successful") {
                it("Should return list of comments") {
                    val expectedResponse = listOf(mock(Comment::class.java))
                    `when`(endpoint.getFireBaseIosUserComments(123)).thenReturn(Single.create {
                        it.onSuccess(
                            expectedResponse
                        )
                    })
                    endpoint.getFireBaseIosUserComments(123)
                        .test()
                        .assertComplete()
                        .assertNoErrors()
                        .assertValue(expectedResponse)

                    verify(endpoint).getFireBaseIosUserComments(123)
                }
            }

            test("When firebase ios user comment api failed") {
                it("Should return error") {
                    val body = Response.error<Comment>(
                        400,
                        "{\"key\":[\"something went wrong\"]}".toResponseBody("application/json".toMediaTypeOrNull())
                    )
                    val exception = HttpException(body)

                    `when`(endpoint.getFireBaseIosUserComments(1234)).thenReturn(Single.error(exception))
                    endpoint.getFireBaseIosUserComments(1234)
                        .test()
                        .assertValueCount(0)
                        .assertError { it is HttpException }

                    verify(endpoint).getFireBaseIosUserComments(1234)
                }
            }
        }
    }
})