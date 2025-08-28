package com.example.fooddeals.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.fooddeals.R
import com.example.fooddeals.model.Restaurant
import com.example.fooddeals.navigation.Screen
import com.example.fooddeals.util.AppUtils.getArrivalText
import com.example.fooddeals.util.AppUtils.getDiscountText

/**
* List view item - Composable component to add items to the list on the Main screen and
* to handle the click even on item to move to next screen
*
* @param navController
* @param restaurant
* @param onItemClicked
* @receiver
*/
@Composable
fun RestaurantItemView(
    navController: NavController,
    restaurant: Restaurant,
    onItemClicked: (item: Restaurant) -> Unit,
) {
    val discountTxt = getDiscountText(restaurant.deals)
    val arrivalTxt = getArrivalText(restaurant)
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp, horizontal = 20.dp)
            .clickable{
                onItemClicked(restaurant)
                navController.navigate(Screen.Details.route)
            },
    ) {
        Column(Modifier.background(Color.White)) {
            // Box to stack the image and the deal banner
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            ) {
                // Restaurant Image
                AsyncImage(
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(8.dp)),
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(restaurant.imageLink)
                        .build(),
                    contentDescription = "",
                    placeholder = painterResource(id = R.drawable.ic_placeholder),
                    error = painterResource(id = R.drawable.ic_error),
                    alignment = Alignment.CenterStart,
                    contentScale = ContentScale.Crop
                )
                // Deal Banner overlay
                Box(
                    modifier = Modifier
                        .padding(10.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color(0xFFE53935))
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Column(modifier= Modifier.padding(0.dp)) {
                        Text(
                            text = discountTxt,
                            color = Color.White,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                        )
                        Text(
                            text = arrivalTxt,
                            color = Color.White,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Normal,
                        )
                    }
                }
            }

            // Restaurant Information and Favorite Button
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Details Column
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = restaurant.name,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = restaurant.address1,
                        fontSize = 15.sp,
                        color = Color.Gray,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = restaurant.cuisines.joinToString(),
                        fontSize = 12.sp,
                        color = Color.DarkGray,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                    // Delivery options row
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        if (restaurant.deals[0].dineIn) {
                            Text(
                                text = "Dine In",
                                fontSize = 15.sp,
                                color = Color.Gray,
                                fontWeight = FontWeight.Normal
                            )
                            Text(
                                text = " ● ", color = Color.Gray,
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Normal
                            )
                        }
                        Text(
                            text = "Takeaway",
                            fontSize = 15.sp,
                            color = Color.Gray,
                            fontWeight = FontWeight.Normal
                        )
                        Text(
                            text = " ● ", color = Color.Gray,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Normal
                        )
                        Text(
                            text = "Order Online",
                            color = Color.Gray,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Normal
                        )
                    }
                }
                // Favorite Button
                IconButton(onClick = { /* Handle favorite click */ }, modifier = Modifier.absoluteOffset(0.dp)) {
                    Icon(
                        imageVector = Icons.Default.FavoriteBorder,
                        contentDescription = "Favorite",
                        tint = Color.Gray
                    )
                }
            }
        }
    }
}