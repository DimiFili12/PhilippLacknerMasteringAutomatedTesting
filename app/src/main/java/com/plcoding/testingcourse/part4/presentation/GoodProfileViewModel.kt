package com.plcoding.testingcourse.part4.presentation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import com.plcoding.testingcourse.R
import com.plcoding.testingcourse.part1.domain.AnalyticsLogger
import com.plcoding.testingcourse.part1.domain.LogParam
import com.plcoding.testingcourse.ui.theme.TestingCourseTheme

class GoodProfileViewModel(
    private val analytics: AnalyticsLogger
) : ViewModel() {

    var state by mutableStateOf(GoodProfileState())
        private set

    fun saveProfile() {
        analytics.logEvent(
            "save_profile",
            LogParam("profile_id", state.profileId),
            LogParam("username", state.username)
        )
        state = state.copy(
            infoMessage = UiText.StringResource(R.string.successfully_saved_profile)
        )
    }
}


// just for visualizing the change from BadProfileViewModel to GoodProfileViewModel
@Composable
fun Test(infoMessage: UiText?) {
    infoMessage?.let {
        Text(text = it.asString())
    }
}

@Preview(showBackground = true)
@Composable
private fun TestOnlyPreview() {
    TestingCourseTheme {
        Test(
            infoMessage = UiText.StringResource(R.string.successfully_saved_profile)
        )
    }
}