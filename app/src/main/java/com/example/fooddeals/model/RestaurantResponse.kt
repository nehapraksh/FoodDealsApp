package com.example.fooddeals.model


data class RestaurantResponse(
    val restaurants: List<Restaurant>
)


data class Restaurant(
    val objectId: String,
    val name: String,
    val address1: String,
    val suburb: String,
    val cuisines: List<String>,
    val imageLink: String,
    val open:String,
    val close : String,
    val deals:List<Deals>
)

data class Deals(
    val objectId: String,
    val discount: Int,
    val dineIn: Boolean,
    val lightning: Boolean,
    val open:String?,
    val close : String?,
    val start:String?,
    val end : String?,
    val qtyLeft : Int,
)
