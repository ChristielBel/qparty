package com.example.qparty

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.qparty.data.ThemeRepository
import com.example.qparty.navigation.QuestionNavGraph
import com.example.qparty.navigation.Routes
import com.example.qparty.ui.components.TopAppBar
import com.example.qparty.ui.theme.QpartyTheme
import com.example.qparty.viewmodel.ThemeViewModel

class MainActivity : ComponentActivity() {
    private val themeRepo by lazy { ThemeRepository(this) }
    private val themeViewModel by viewModels<ThemeViewModel> {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return ThemeViewModel(themeRepo) as T
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val isDarkTheme by themeViewModel.isDarkTheme.collectAsState()

            QpartyTheme(darkTheme = isDarkTheme) {
                QuestionGameApp(
                    isDarkTheme = isDarkTheme,
                    onToggleTheme = { themeViewModel.toggleTheme() }
                )
            }
        }
    }
}

@Composable
fun QuestionGameApp(
    isDarkTheme: Boolean,
    onToggleTheme: () -> Unit
) {
    val navController = rememberNavController()
    val currentBackStackEntry = navController.currentBackStackEntryAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                navController = navController,
                showBackButton = currentBackStackEntry.value?.destination?.route == Routes.GAME,
                isDarkTheme = isDarkTheme,
                onToggleTheme = onToggleTheme
            )
        }
    ) { innerPadding ->
        QuestionNavGraph(
            navController = navController,
            modifier = Modifier.padding(innerPadding)
        )
    }
}