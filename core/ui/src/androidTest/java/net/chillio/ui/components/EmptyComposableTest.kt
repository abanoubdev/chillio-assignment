package net.chillio.ui.components

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class EmptyComposableTest {

    @get:Rule
    val rule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun emptyComposable_showEmptyContent() {
        rule.setContent {
            EmptyContent(
                title = "No employees"
            )
        }
        rule.onNodeWithText("No employees").assertExists()
    }
}
