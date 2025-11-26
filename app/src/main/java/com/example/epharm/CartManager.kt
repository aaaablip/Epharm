package com.example.epharm

object CartManager {
    private val cartItems = mutableListOf<Product>()

    fun addProduct(product: Product) {
        cartItems.add(product)
    }

    fun removeProduct(product: Product) {
        cartItems.remove(product)
    }

    fun clearCart() {
        cartItems.clear()
    }

    fun getCartItems(): List<Product> = cartItems.toList()
}