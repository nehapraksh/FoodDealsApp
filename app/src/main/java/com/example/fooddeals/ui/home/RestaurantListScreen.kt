package com.example.fooddeals.ui.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.fooddeals.R
import com.example.fooddeals.model.Restaurant
import com.example.fooddeals.viewmodel.RestaurantsViewModel


/**
 * Restaurant list - Composable component to set the view on the Home Screen
 *
 * @param navController
 * @param restaurantViewModel
 */
@ExperimentalFoundationApi
@Composable
fun RestaurantListScreen(
    navController: NavHostController, restaurantViewModel: RestaurantsViewModel
)
{
// set the view if the api call is in the loading state
    if (restaurantViewModel.loading)
    {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator()
        }
    }
    else if (restaurantViewModel.errorMessage.isNotEmpty())
    { // set the view if the api response data is set to error
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = restaurantViewModel.errorMessage,
                style = MaterialTheme.typography.labelMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
    else // set the view if the api response is success
        RestaurantList(
            navController = navController,
            restaurantList = restaurantViewModel.restaurantsList,
            onItemClicked = restaurantViewModel::itemClicked
        )
}

/**
 * Restaurant list - Composable component to set items view on the Main/Home screen
 *
 * @param navController
 * @param restaurantList
 * @param onItemClicked
 * @receiver
 */
@OptIn(ExperimentalMaterial3Api::class)
@ExperimentalFoundationApi
@Composable
fun RestaurantList(
    navController: NavController,
    restaurantList: List<Restaurant>,
    onItemClicked: (item: Restaurant) -> Unit
)
{
    // Mutable state to hold the search query
    var searchKey by remember { mutableStateOf("") }
    val searchedRestaurants = remember(searchKey, restaurantList) {
        if (searchKey.isBlank()) {
            restaurantList
        } else {
            restaurantList.filter { restaurant ->
                restaurant.name.contains(searchKey, ignoreCase = true) ||
                        restaurant.cuisines.any { cuisin ->
                            cuisin.contains(searchKey, ignoreCase = true)
                        }
            }
        }
    }
    val listState = rememberLazyListState()
    Column(Modifier.background(Color.White)) {
        Spacer(modifier = Modifier.height(5.dp))
        TopView()
        // Search Bar using OutlinedTextField
        OutlinedTextField(
            value = searchKey,
            onValueChange = { searchKey = it },
            placeholder = { Text("e.g. chinese, pizza", color = Color.LightGray) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_search), // Replace with your search icon
                    contentDescription = "Search",
                    tint = Color.DarkGray,
                )
            },
            shape = RoundedCornerShape(0.dp)
        )
        LazyColumn(state = listState, modifier = Modifier.padding(top = 0.dp, bottom = 48.dp)) {
            // add items to the list
            itemsIndexed(searchedRestaurants) { index, item ->
                RestaurantItemView(
                    navController = navController,
                    restaurant = item,
                    onItemClicked = onItemClicked
                )
            }
        }
    }
}


/**
 * Top view of the list - Composable component to set the header/toolbar of the screen
 *
 */
@Composable
fun TopView()
{
    // Top View Row
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 48.dp, bottom = 5.dp, start = 20.dp, end = 20.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        // Profile Icon
        Icon(
            painter = painterResource(id = R.drawable.ic_profile),
            contentDescription = "Profile",
            tint = Color.Gray,
            modifier = Modifier.size(36.dp)
        )
        // App Logo
        Icon(
            painter = painterResource(id = R.drawable.ic_logo),
            contentDescription = "Logo",
            tint = Color(0xFFE53935),
            modifier = Modifier.size(36.dp)
        )
        // Filters Icon
        Icon(
            painter = painterResource(id = R.drawable.ic_filter),
            contentDescription = "Filters",
            tint = Color.Gray,
            modifier = Modifier.size(36.dp)
        )
    }

    Spacer(modifier = Modifier.height(16.dp))
}