package com.example.fooddeals.navigation

import androidx.annotation.StringRes
import com.example.fooddeals.R

sealed class Screen(val route: String, @StringRes val resourceId: Int) {
    object Home : Screen("restaurantListScreen", R.string.text_home)
    object Details : Screen("restaurantDetailsScreen", R.string.text_details)
}