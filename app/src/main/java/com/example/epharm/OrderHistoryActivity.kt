package com.example.epharm

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.epharm.databinding.ActivityOrderHistoryBinding

class OrderHistoryActivity : BaseActivity() {

    private lateinit var binding: ActivityOrderHistoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = ActivityOrderHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backButton.setOnClickListener {
            onBackPressed()
        }

        val orderHistory = OrderManager.getOrderHistory()

        if (orderHistory.isEmpty()) {
            binding.emptyOrdersTextView.visibility = View.VISIBLE
            binding.orderHistoryRecyclerView.visibility = View.GONE
        } else {
            binding.emptyOrdersTextView.visibility = View.GONE
            binding.orderHistoryRecyclerView.visibility = View.VISIBLE
        }

        val adapter = OrderHistoryAdapter(orderHistory)
        binding.orderHistoryRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.orderHistoryRecyclerView.adapter = adapter
    }
}