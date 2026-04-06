package com.plcoding.testingcourse.part7.data

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isTrue
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class UserRepositoryImplTest {
    private lateinit var repository: UserRepositoryImpl
    private lateinit var api: UserApiFake

    @BeforeEach
    fun setUp() {
        api = UserApiFake()
        repository = UserRepositoryImpl(api)
    }

    @Test
    fun `Test getting profile`() = runBlocking {
        val userId = "1"
        val profileResult = repository.getProfile(userId = userId)

        assertThat(profileResult.isSuccess).isTrue()
        assertThat(profileResult.getOrThrow().user.id).isEqualTo(userId)

        val expectedPosts = api.posts.filter { it.userId == userId }
        assertThat(profileResult.getOrThrow().posts).isEqualTo(expectedPosts)
    }
}