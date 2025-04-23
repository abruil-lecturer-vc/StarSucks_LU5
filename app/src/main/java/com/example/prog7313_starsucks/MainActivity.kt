package com.example.prog7313_starsucks

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.lifecycle.lifecycleScope
import com.example.prog7313_starsucks.Product
import com.example.prog7313_starsucks.databinding.ActivityMainWithNavDrawerBinding
import com.google.android.material.navigation.NavigationView
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity(), View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {
    // var order = Order()
    private lateinit var orderDAO: OrderDAO
    private lateinit var db: AppDatabase
    private lateinit var binding: ActivityMainWithNavDrawerBinding
    private lateinit var database: FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainWithNavDrawerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.navToolbar)

        // Initialise the DB and DAO
        db = AppDatabase.getDatabase(this) as AppDatabase
        orderDAO = db.orderDAO()

        // Setting up realtime db
        database = FirebaseDatabase.getInstance()

        setupListeners()
        setupNavigation()

        initializeProducts() // Populate the database
    }


    // Insert products into the Room database
    private fun initializeProducts() {

        lifecycleScope.launch {
            if (orderDAO.getAllOrders().isEmpty()) {
                orderDAO.insertAll(
                    listOf(
                        Product(name = "Soy Latte", price = 4.5),
                        Product(name = "Chocco Frapp", price = 5.0),
                        Product(name = "Bottled Americano", price = 3.5),
                        Product(name = "Rainbow Frapp", price = 5.5),
                        Product(name = "Caramel Frapp", price = 4.8),
                        Product(name = "Black Forest Frapp", price = 6.0)
                    )
                )
            }
        }
    }

    // Set up listeners for drink clicks
    private fun setupListeners() {
        listOf(
            Triple(binding.imgSb1, "Soy Latte", 4.5),
            Triple(binding.imgSb2, "Chocco Frapp", 5.0),
            Triple(binding.imgSb3, "Bottled Americano", 3.5),
            Triple(binding.imgSb4, "Rainbow Frapp", 5.5),
            Triple(binding.imgSb5, "Caramel Frapp", 4.8),
            Triple(binding.imgSb6, "Black Forest Frapp", 6.0)
        ).forEach { (view, name, price) ->
            view.setOnClickListener { saveOrder(name, price) }
        }
    }

    // Set up for navigation toolbar
    private fun setupNavigation() {
        val toggle = ActionBarDrawerToggle(
            this, binding.drawerLayout, binding.navToolbar,
            R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        binding.navView.bringToFront()
        binding.navView.setNavigationItemSelectedListener(this)
    }

    // product gets saved to database that user clicked
    private fun saveOrder(name: String, price: Double) {
        lifecycleScope.launch {
            val product = Product(name = name, price = price)
            orderDAO.insert(product)

            Toast.makeText(this@MainActivity, "Ordered: $name", Toast.LENGTH_SHORT).show()

            saveOrderToFirebase(product)

            val intent = Intent(this@MainActivity, OrderDetailsActivity::class.java)
            intent.putExtra("order", name)
            startActivity(intent)
        }
    }

    private fun saveOrderToFirebase(product: Product) {
        // Grab reference to "orders" node in db
        val ordersRef = database.getReference("orders")

        // Get unique key for new order
        val orderId = ordersRef.push().key

        orderId?.let { id ->
            ordersRef.child(id).setValue(product).addOnSuccessListener {
                Toast.makeText(this, "Order saved to Firebase", Toast.LENGTH_SHORT).show()
            }.addOnFailureListener { e ->
                Toast.makeText(this, "Failed to save order: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.nav_main -> openIntent(this, "", MainActivity::class.java)
            R.id.nav_photo -> openIntent(this, "", CoffeeSnapsActivity::class.java)
            // R.id.nav_history -> openIntent(this, "", ::class.java)
        }

        binding.drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onClick(v: View?) {
        TODO("Not yet implemented")
    }


}
