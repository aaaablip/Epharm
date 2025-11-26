package com.example.epharm

import java.util.Date

data class Order(
    val orderId: String,
    val items: List<Product>,
    val total: Double,
    val date: Date,
    val paymentMethod: String
)