package net.chillio.ui.components

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.CoreMatchers.`is`
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ErrorComposableTest {

    @get:Rule
    val rule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun errorComposable_retryCallsCallback() {
        var retried = false
        rule.setContent {
            ErrorContent(
                title = "Something went wrong",
                onRetry = { retried = true }
            )
        }
        rule.onNodeWithText("Something went wrong").assertExists()
        rule.onNodeWithText("Retry").performClick()
        rule.runOnIdle {
            assertThat(retried, `is`(true))
        }
    }
}
