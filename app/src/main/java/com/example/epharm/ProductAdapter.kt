package com.example.epharm

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.epharm.databinding.ItemProductBinding

class ProductAdapter(
    private var products: List<Product>,
    private val onAddToCartClicked: (Product) -> Unit
) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>(), Filterable {

    private var filteredProducts: List<Product> = products

    inner class ProductViewHolder(val binding: ItemProductBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(product: Product) {
            binding.productName.text = product.name
            binding.productPrice.text = product.price
            binding.productImage.setImageResource(product.imageResId)

            binding.addToCartButton.setOnClickListener {
                onAddToCartClicked(product)
                Toast.makeText(binding.root.context, "${product.name} added to cart", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(filteredProducts[position])
    }

    override fun getItemCount() = filteredProducts.size

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charString = constraint?.toString() ?: ""
                filteredProducts = if (charString.isEmpty()) products else {
                    products.filter {
                        it.name.contains(charString, true) ||
                        it.description.contains(charString, true)
                    }
                }
                val filterResults = FilterResults()
                filterResults.values = filteredProducts
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filteredProducts = if (results?.values == null) ArrayList() else results.values as List<Product>
                notifyDataSetChanged()
            }
        }
    }
}