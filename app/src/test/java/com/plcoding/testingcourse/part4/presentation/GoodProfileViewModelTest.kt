package com.plcoding.testingcourse.part4.presentation

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.plcoding.testingcourse.R
import com.plcoding.testingcourse.part1.domain.AnalyticsLogger
import com.plcoding.testingcourse.part1.domain.LogParam
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class GoodProfileViewModelTest {
    private lateinit var viewModel: GoodProfileViewModel

    @BeforeEach
    fun setUp() {
        viewModel = GoodProfileViewModel(
            analytics = object : AnalyticsLogger {
                override fun logEvent(
                    key: String,
                    vararg params: LogParam<Any>
                ) = Unit
            }
        )
    }

    @Test
    fun `Test saving profile`() {
        viewModel.saveProfile()

        val state = viewModel.state
        assertThat(state.infoMessage)
            .isEqualTo(UiText.StringResource(R.string.successfully_saved_profile))
    }
}