package net.chillio.ui.employeeList

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class EmployeeListItemTest {

    @get:Rule
    val rule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun employeeRow_showsNameAndTeam() {
        rule.setContent {
            EmployeeListItemRow(
                imageUrl = "https://s3.amazonaws.com/sq-mobile-interview/photos/16c00560-6dd3-4af4-97a6-d4754e7f2394/small.jpg",
                name = "Justine Mason",
                team = "Point of Sale",
            )
        }
        rule.onNodeWithText("Justine Mason").assertExists()
        rule.onNodeWithText("Point of Sale").assertExists()
    }
}