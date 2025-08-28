package com.example.fooddeals.util

import com.example.fooddeals.model.Deals
import com.example.fooddeals.model.Restaurant

object AppUtils
{
    fun getDiscountText(deals: List<Deals>): String
    {
        var discountTxt: String = "No deal"
        // Find the highest discount from the discount
        val highestDiscountDeal = deals.maxByOrNull { it.discount }
        if (highestDiscountDeal != null)
        {
            discountTxt = if (highestDiscountDeal.dineIn)
            {
                "${highestDiscountDeal.discount}% off - Dine In"
            }
            else
            {
                "${highestDiscountDeal.discount}% off"
            }
        }
        return discountTxt
    }

    fun getArrivalText(restaurant: Restaurant): String
    {
        var arrivalTxt = ""
        // Find the highest discount from the discount
        val highestDiscountDeal = restaurant.deals.maxByOrNull { it.discount }
        if (highestDiscountDeal != null)
        {
            arrivalTxt = if (highestDiscountDeal.start != null && highestDiscountDeal.end != null)
            {
                "Between ${highestDiscountDeal.start} - ${highestDiscountDeal.end}"
            }
            else if (highestDiscountDeal.open != null && highestDiscountDeal.close != null)
            {
                if (highestDiscountDeal.open == restaurant.open && highestDiscountDeal.close == restaurant.close)
                {
                    "Anytime today"
                }
                else
                {
                    "Between ${highestDiscountDeal.open} - ${highestDiscountDeal.close}"
                }
            }
            else
            {
                "Between ${restaurant.open} - ${restaurant.close}"
            }
        }
        return arrivalTxt
    }

    fun getDealAvailableTime(deal: Deals): String?
    {
        return if (deal.start != null && deal.end != null)
        {
            "Between ${deal.start} - ${deal.end}"
        }
        else if (deal.open != null && deal.close != null)
        {
            "Between ${deal.open} - ${deal.close}"
        }else null
    }
}