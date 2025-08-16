package com.example.qparty

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.qparty.navigation.QuestionNavGraph
import com.example.qparty.navigation.Routes
import com.example.qparty.ui.components.TopAppBar
import com.example.qparty.ui.theme.QpartyTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            QpartyTheme {
                QuestionGameApp()
            }
        }
    }
}

@Composable
fun QuestionGameApp() {
    val navController = rememberNavController()
    val currentBackStackEntry = navController.currentBackStackEntryAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                navController = navController,
                showBackButton = currentBackStackEntry.value?.destination?.route == Routes.GAME
            )
        }
    ) { innerPadding ->
        QuestionNavGraph(
            navController = navController,
            modifier = Modifier.padding(innerPadding)
        )
    }
}