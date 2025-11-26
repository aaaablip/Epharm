package com.example.epharm

object OrderManager {
    private val orderHistory = mutableListOf<Order>()

    fun addOrder(order: Order) {
        orderHistory.add(order)
    }

    fun getOrderHistory(): List<Order> = orderHistory.toList()
}