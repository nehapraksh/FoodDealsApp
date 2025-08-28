package com.example.fooddeals.ui.details


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.fooddeals.R
import com.example.fooddeals.model.Deals
import com.example.fooddeals.model.Restaurant
import com.example.fooddeals.ui.home.TopView
import com.example.fooddeals.util.AppUtils.getDealAvailableTime


/**
 * Restaurant details - Composable component to show the selected restaurant details on the Detail screen
 *
 * @param restaurant
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RestaurantDetailsScreen(restaurant: Restaurant)
{
    Column(modifier = Modifier.background(Color.White)){
        TopView()
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 0.dp, bottom = 48.dp)
                .background(Color.White)
        ) {
            // Header Image with a "New" badge
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                ) {
                    // Placeholder for the main restaurant image
                    AsyncImage(
                        modifier = Modifier
                            .fillMaxSize(),
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(restaurant.imageLink)
                            .build(),
                        contentDescription = "",
                        placeholder = painterResource(id = R.drawable.ic_placeholder),
                        error = painterResource(id = R.drawable.ic_error),
                        alignment = Alignment.Center,
                        contentScale = ContentScale.Crop
                    )

                    // "New" badge in the top-right corner
                    Row(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(16.dp)
                            .background(
                                color = Color.Red,
                                shape = RoundedCornerShape(
                                    topStart = 5.dp,
                                    bottomStart = 5.dp,
                                    topEnd = 5.dp,
                                    bottomEnd = 5.dp
                                )
                            )
                            .padding(horizontal = 8.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = "New",
                            tint = Color.White,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(Modifier.width(4.dp))
                        Text(
                            text = "New",
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            item {
                ContactMethodsView()
            }

            // Main Content Area
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .background(Color.White),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    HorizontalDivider(
                        color = Color.LightGray,
                        thickness = 1.dp,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                    // Restaurant Name
                    Text(
                        text = restaurant.name,
                        style = MaterialTheme.typography.headlineLarge,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                    Spacer(Modifier.height(4.dp))

                    // Cuisine and pricing info
                    Text(
                        text = "${restaurant.cuisines.joinToString(separator = " ● ")} ● $",
                        color = Color.Gray
                    )
                }
            }

            // Hours and Location Section
            item {
                Column {
                    HorizontalDivider(
                        color = Color.LightGray,
                        thickness = 1.dp,
                        modifier = Modifier.padding(vertical = 16.dp, horizontal = 20.dp)
                    )
                    Spacer(Modifier.height(8.dp))
                    InfoRow(
                        icon = Icons.Outlined.DateRange,
                        title = "Hours: ${restaurant.open} - ${restaurant.close}"
                    )
                    Spacer(Modifier.height(10.dp))
                    InfoRow(
                        icon = Icons.Outlined.LocationOn,
                        title = restaurant.address1,
                    )
                }
            }

            // deals Section
            items(restaurant.deals.sortedByDescending { it.discount }) { deal ->
                DealItem(deal = deal, restaurant = restaurant)
            }
        }
    }
}

/**
 * Composable component to show the restaurant hours and address details
 *
 * @param icon
 * @param title
 */
@Composable
fun InfoRow(icon: ImageVector, title: String)
{
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = Color.Gray,
            modifier = Modifier.size(24.dp)
        )
        Spacer(Modifier.width(16.dp))
        Text(text = title)
    }
}

/**
 * Composable component to show all the deals available
 *
 * @param deal
 */
@Composable
fun DealItem(deal: Deals, restaurant: Restaurant)
{
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 5.dp),
        shape = RoundedCornerShape(0.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White) // Light red background
    ) {
        Column {
            HorizontalDivider(
                thickness = 1.dp,
                color = Color.LightGray,
                modifier = Modifier.padding(16.dp)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Spacer(Modifier.width(16.dp))
                    Column {
                        Text(
                            text = "${deal.discount}% Off",
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Red
                        )
                        if( getDealAvailableTime(deal = deal)!=null)
                        {
                            Text(text = getDealAvailableTime(deal = deal)!!, color = Color.Gray)
                        }
                        Text(
                            text = "${deal.qtyLeft} Deals Left",
                            fontSize = 12.sp,
                            color = Color.LightGray
                        )
                    }
                }
                Button(
                    onClick = { /* Handle redeem btn click */ },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                    shape = RoundedCornerShape(20.dp),
                    border = BorderStroke(color = Color.Red, width = 2.dp),
                    modifier = Modifier.padding(end = 10.dp)
                ) {
                    Text(text = "Redeem", color = Color.Red)
                }
            }
        }
    }
}

/**
 * Restaurant contact details view
 *
 */
@Composable
fun ContactMethodsView()
{
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(vertical = 8.dp, horizontal = 15.dp),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        ContactItem(icon = Icons.Default.AccountBox, label = "Menu")
        ContactItem(icon = Icons.Default.Phone, label = "Call us")
        ContactItem(icon = Icons.Default.LocationOn, label = "Location")
        ContactItem(icon = Icons.Default.FavoriteBorder, label = "Favourite")
    }
}

/**
 * Restaurant each contact method card - Composable component to show the all contact methods
 *
 * @param icon
 * @param label
 */
@Composable
fun ContactItem(icon: Any, label: String)
{
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Use painterResource for the menu icon as it's not a standard M3 vector icon
        if (icon is ImageVector)
        {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = Color.DarkGray,
                modifier = Modifier.size(24.dp)
            )
        }
        else if (icon is Painter)
        {
            Image(
                painter = icon,
                contentDescription = label,
                modifier = Modifier.size(24.dp)
            )
        }
        Text(text = label, fontSize = 12.sp)
    }
}
