package com.example.epharm

import android.content.Intent
import android.os.Bundle
import android.widget.SearchView
import androidx.recyclerview.widget.GridLayoutManager
import com.example.epharm.databinding.ActivityMainBinding

class MainActivity : BaseActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var productAdapter: ProductAdapter

    private val medicines = listOf(
        Product("Paracetamol", "₱20.00", R.drawable.ic_paracetamol, "Pain relief."),
        Product("Ibuprofen", "₱35.50", R.drawable.ic_ibuprofen, "Anti-inflammatory."),
        Product("Vitamin C", "₱50.00", R.drawable.ic_vitamin_c, "Boosts immunity."),
        Product("Amoxicillin", "₱80.75", R.drawable.ic_amoxicillin, "Antibiotic."),
        Product("Cetirizine", "₱45.25", R.drawable.ic_cetirizine, "Allergy relief."),
        Product("Loperamide", "₱30.00", R.drawable.ic_loperamide, "Anti-diarrhea."),
        Product("Aspirin", "₱40.00", R.drawable.ic_aspirin, "For pain and fever relief."),
        Product("Meclizine", "₱55.00", R.drawable.ic_meclizine, "For motion sickness."),
        Product("Dextromethorphan", "₱75.00", R.drawable.ic_dextromethorphan, "Cough suppressant."),
        Product("Guaifenesin", "₱65.00", R.drawable.ic_guaifenesin, "Expectorant for chest congestion."),
        Product("Pseudoephedrine", "₱85.00", R.drawable.ic_pseudoephedrine, "Decongestant."),
        Product("Diphenhydramine", "₱45.00", R.drawable.ic_diphenhydramine, "Antihistamine for allergies."),
        Product("Omeprazole", "₱95.00", R.drawable.ic_omeprazole, "For heartburn and acid reflux."),
        Product("Fexofenadine", "₱90.00", R.drawable.ic_fexofenadine, "Non-drowsy antihistamine."),
        Product("Bisacodyl", "₱50.00", R.drawable.ic_bisacodyl, "Stimulant laxative."),
        Product("Loratadine", "₱60.00", R.drawable.ic_loratadine, "Non-drowsy antihistamine for allergies.")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        productAdapter = ProductAdapter(medicines) { product ->
            CartManager.addProduct(product)
        }

        binding.productRecyclerView.layoutManager = GridLayoutManager(this, 2)
        binding.productRecyclerView.adapter = productAdapter

        binding.fabCart.setOnClickListener {
            startActivity(Intent(this, CartActivity::class.java))
        }

        binding.fabUploadPrescription.setOnClickListener {
            startActivity(Intent(this, UploadPrescriptionActivity::class.java))
        }

        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_orders -> {
                    startActivity(Intent(this, OrderHistoryActivity::class.java))
                    true
                }
                R.id.navigation_profile -> {
                    startActivity(Intent(this, ProfileActivity::class.java))
                    true
                }
                else -> false
            }
        }

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                productAdapter.filter.filter(newText)
                return false
            }
        })
    }
}