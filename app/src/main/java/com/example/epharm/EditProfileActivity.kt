package com.example.epharm

import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import com.example.epharm.databinding.ActivityEditProfileBinding

class EditProfileActivity : BaseActivity() {

    private lateinit var binding: ActivityEditProfileBinding
    private lateinit var sharedPref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPref = getSharedPreferences("user_prefs", MODE_PRIVATE)

        loadUserData()

        binding.backButton.setOnClickListener {
            onBackPressed()
        }

        binding.saveButton.setOnClickListener {
            saveUserData()
        }
    }

    private fun loadUserData() {
        val name = sharedPref.getString("name", "")
        val shippingAddress = sharedPref.getString("shipping_address", "")

        binding.nameEditText.setText(name)
        binding.shippingAddressEditText.setText(shippingAddress)
    }

    private fun saveUserData() {
        val name = binding.nameEditText.text.toString().trim()
        val shippingAddress = binding.shippingAddressEditText.text.toString().trim()

        with(sharedPref.edit()) {
            putString("name", name)
            putString("shipping_address", shippingAddress)
            apply()
        }

        Toast.makeText(this, "Profile updated successfully!", Toast.LENGTH_SHORT).show()
        finish()
    }
}