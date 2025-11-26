package com.example.epharm

import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.epharm.databinding.ActivityCheckoutBinding
import java.util.Date
import java.util.UUID

class CheckoutActivity : BaseActivity() {

    private lateinit var binding: ActivityCheckoutBinding
    private lateinit var sharedPref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = ActivityCheckoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPref = getSharedPreferences("user_prefs", MODE_PRIVATE)

        loadUserData()

        binding.backButton.setOnClickListener {
            onBackPressed()
        }

        binding.paymentMethodGroup.setOnCheckedChangeListener { _, checkedId ->
            binding.creditCardLayout.visibility = View.GONE
            binding.gcashLayout.visibility = View.GONE

            when (checkedId) {
                R.id.credit_card_radio -> {
                    binding.creditCardLayout.visibility = View.VISIBLE
                }
                R.id.gcash_radio -> {
                    binding.gcashLayout.visibility = View.VISIBLE
                }
            }
        }

        binding.placeOrderButton.setOnClickListener {
            val selectedPaymentMethodId = binding.paymentMethodGroup.checkedRadioButtonId
            if (selectedPaymentMethodId == -1) {
                Toast.makeText(this, "Please select a payment method.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val paymentMethod = when (selectedPaymentMethodId) {
                R.id.credit_card_radio -> "Credit/Debit Card"
                R.id.cod_radio -> "Cash on Delivery"
                R.id.gcash_radio -> "GCash"
                else -> ""
            }

            val cartItems = CartManager.getCartItems()
            val total = cartItems.sumOf { it.price.replace("₱", "").toDoubleOrNull() ?: 0.0 }

            val order = Order(
                orderId = UUID.randomUUID().toString(),
                items = cartItems,
                total = total,
                date = Date(),
                paymentMethod = paymentMethod
            )

            OrderManager.addOrder(order)
            CartManager.clearCart()

            Toast.makeText(this, "Order placed successfully with $paymentMethod!", Toast.LENGTH_LONG).show()
            finish()
        }
    }

    private fun loadUserData() {
        val name = sharedPref.getString("name", "")
        val shippingAddress = sharedPref.getString("shipping_address", "")

        binding.nameEditText.setText(name)
        binding.addressEditText.setText(shippingAddress)
    }
}