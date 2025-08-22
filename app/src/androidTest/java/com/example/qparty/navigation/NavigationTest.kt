import android.content.Context
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.platform.app.InstrumentationRegistry
import com.example.qparty.R
import com.example.qparty.navigation.QuestionNavGraph
import com.example.qparty.navigation.Routes
import com.example.qparty.ui.theme.QpartyTheme
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class NavigationTest {

    val context: Context = InstrumentationRegistry.getInstrumentation().targetContext
    val button = context.getString(R.string.start_game_button)

    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var navController: TestNavHostController

    @Test
    fun startScreenButton_navigatesToGame() {
        val context = ApplicationProvider.getApplicationContext<android.content.Context>()
        navController = TestNavHostController(context).apply {
            navigatorProvider.addNavigator(ComposeNavigator())
        }

        composeTestRule.setContent {
            QpartyTheme {
                QuestionNavGraph(navController = navController)
            }
        }

        assertEquals(Routes.START, navController.currentDestination?.route)

        composeTestRule.onNodeWithText(button).performClick()

        composeTestRule.runOnIdle {
            assertEquals(Routes.GAME, navController.currentDestination?.route)
        }
    }

    @Test
    fun navBack_returnsToStartScreen() {
        val context = ApplicationProvider.getApplicationContext<android.content.Context>()
        navController = TestNavHostController(context).apply {
            navigatorProvider.addNavigator(ComposeNavigator())
        }

        composeTestRule.setContent {
            QpartyTheme {
                QuestionNavGraph(navController = navController)
            }
        }

        composeTestRule.runOnIdle {
            navController.navigate(Routes.GAME)
        }

        assertEquals(Routes.GAME, navController.currentDestination?.route)

        composeTestRule.runOnIdle {
            navController.navigateUp()
        }

        assertEquals(Routes.START, navController.currentDestination?.route)
    }

    @Test
    fun navGraph_startsAtStartScreen() {
        val context = ApplicationProvider.getApplicationContext<android.content.Context>()
        navController = TestNavHostController(context).apply {
            navigatorProvider.addNavigator(ComposeNavigator())
        }

        composeTestRule.setContent {
            QpartyTheme {
                QuestionNavGraph(navController = navController)
            }
        }

        assertEquals(Routes.START, navController.currentDestination?.route)
        composeTestRule.onNodeWithText(button).assertExists()
    }
}