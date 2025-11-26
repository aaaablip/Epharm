package com.example.epharm

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import com.example.epharm.databinding.ActivityProfileBinding

class ProfileActivity : BaseActivity() {

    private lateinit var binding: ActivityProfileBinding
    private lateinit var settingsPref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        settingsPref = getSharedPreferences("settings_prefs", MODE_PRIVATE)

        setupDarkModeSwitch()

        binding.backButton.setOnClickListener {
            onBackPressed()
        }

        binding.editProfileButton.setOnClickListener {
            startActivity(Intent(this, EditProfileActivity::class.java))
        }

        binding.logoutButton.setOnClickListener {
            val userPref = getSharedPreferences("user_prefs", MODE_PRIVATE)
            userPref.edit().clear().apply()

            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        loadUserData()
    }

    private fun loadUserData() {
        val userPref = getSharedPreferences("user_prefs", MODE_PRIVATE)
        val name = userPref.getString("name", "User Name")
        val email = userPref.getString("email", "user@example.com")

        binding.userNameTextView.text = name
        binding.userEmailTextView.text = email
    }

    private fun setupDarkModeSwitch() {
        binding.darkModeSwitch.isChecked = settingsPref.getBoolean("dark_mode", false)
        binding.darkModeSwitch.setOnCheckedChangeListener { _, isChecked ->
            with(settingsPref.edit()) {
                putBoolean("dark_mode", isChecked)
                apply()
            }
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
    }
}