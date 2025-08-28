package com.example.fooddeals

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material3.Surface
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.fooddeals.navigation.Screen
import com.example.fooddeals.ui.details.RestaurantDetailsScreen
import com.example.fooddeals.ui.home.RestaurantListScreen
import com.example.fooddeals.ui.theme.FoodDealsTheme
import com.example.fooddeals.viewmodel.RestaurantsViewModel
import dagger.hilt.android.AndroidEntryPoint


/**
 * Main activity - entry point of the application
 *
 */
@OptIn(ExperimentalFoundationApi::class)
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val restaurantsViewModel by viewModels<RestaurantsViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            FoodDealsTheme {
                // A surface container using the 'background' color from the theme
                // handle the navigation components and show the first screen as the Home/Main Screen
                NavHost(navController = navController, startDestination = Screen.Home.route) {
                    composable(Screen.Home.route) {
                        Surface(color = Color.White) {
                            RestaurantListScreen(
                                navController = navController,
                                restaurantViewModel = restaurantsViewModel,
                            )
                        }
                    }
                    // define and handle the navigation to the Details screen when clicked on item from the Home Screen
                    composable(Screen.Details.route) {
                        RestaurantDetailsScreen(restaurantsViewModel.clickedItem)
                    }
                }
            }
        }
    }
}