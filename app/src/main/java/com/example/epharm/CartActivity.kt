package com.example.epharm

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.epharm.databinding.ActivityCartBinding
import java.text.NumberFormat
import java.util.Locale

class CartActivity : BaseActivity() {

    private lateinit var binding: ActivityCartBinding
    private lateinit var cartAdapter: CartAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backButton.setOnClickListener {
            onBackPressed()
        }

        setupRecyclerView()
        updateTotal()

        binding.checkoutButton.setOnClickListener {
            val intent = Intent(this, CheckoutActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setupRecyclerView() {
        val cartItems = CartManager.getCartItems()
        cartAdapter = CartAdapter(cartItems) { product ->
            CartManager.removeProduct(product)
            cartAdapter.updateData(CartManager.getCartItems())
            updateTotal()
        }

        binding.cartRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@CartActivity)
            adapter = cartAdapter
        }
    }

    private fun updateTotal() {
        val cartItems = CartManager.getCartItems()
        if (cartItems.isEmpty()) {
            binding.emptyCartTextView.visibility = View.VISIBLE
            binding.cartRecyclerView.visibility = View.GONE
            binding.checkoutLayout.visibility = View.GONE
        } else {
            binding.emptyCartTextView.visibility = View.GONE
            binding.cartRecyclerView.visibility = View.VISIBLE
            binding.checkoutLayout.visibility = View.VISIBLE
        }

        val total = cartItems.sumOf { product ->
            product.price.replace("₱", "").toDoubleOrNull() ?: 0.0
        }
        val format: NumberFormat = NumberFormat.getCurrencyInstance(Locale("en", "PH"))
        binding.totalPriceTextView.text = "Total: ${format.format(total)}"
    }

    override fun onResume() {
        super.onResume()
        if (::cartAdapter.isInitialized) {
            cartAdapter.updateData(CartManager.getCartItems())
        }
        updateTotal()
    }
}