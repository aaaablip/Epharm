package com.example.epharm

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.epharm.databinding.ItemOrderBinding
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Locale

class OrderHistoryAdapter(private val orders: List<Order>) : RecyclerView.Adapter<OrderHistoryAdapter.OrderViewHolder>() {

    inner class OrderViewHolder(val binding: ItemOrderBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(order: Order) {
            binding.orderIdTextView.text = "Order ID: ${order.orderId.substring(0, 8)}"
            val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            binding.orderDateTextView.text = "Date: ${sdf.format(order.date)}"
            val format: NumberFormat = NumberFormat.getCurrencyInstance(Locale("en", "PH"))
            binding.orderTotalTextView.text = "Total: ${format.format(order.total)}"
            binding.orderPaymentMethodTextView.text = "Payment: ${order.paymentMethod}"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val binding = ItemOrderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OrderViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        holder.bind(orders[position])
    }

    override fun getItemCount() = orders.size
}