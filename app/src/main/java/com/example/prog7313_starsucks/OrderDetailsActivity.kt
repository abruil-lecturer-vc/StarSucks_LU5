package com.example.prog7313_starsucks

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import com.example.prog7313_starsucks.databinding.ActivityOrderDetailsBinding
import com.google.android.material.navigation.NavigationView

class OrderDetailsActivity: AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
   // var order = Order()
    private lateinit var binding: ActivityOrderDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val productName = intent.getStringExtra("order") ?: "Unknown"
        binding.txtPlacedOrder.text = productName

        val imageRes = when (productName) {
            "Soy Latte" -> R.drawable.sb1
            "Chocco Frapp" -> R.drawable.sb2
            "Bottled Americano" -> R.drawable.sb3
            "Rainbow Frapp" -> R.drawable.sb4
            "Caramel Frapp" -> R.drawable.sb5
            "Black Forest Frapp" -> R.drawable.sb6
            else -> R.drawable.starsuckslogo
        }

        binding.imgOrderedDrink.setImageResource(imageRes)

        binding.fab.setOnClickListener {
            shareIntent(applicationContext, productName)
        }

        setupNavigation()
    }

    // Set up for navigation toolbar
    private fun setupNavigation() {
        setSupportActionBar(binding.navToolbar3)
        val toggle = ActionBarDrawerToggle(
            this, binding.drawerLayout3, binding.navToolbar3,
            R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        binding.drawerLayout3.addDrawerListener(toggle)
        toggle.syncState()
        binding.navView3.bringToFront()
        binding.navView3.setNavigationItemSelectedListener(this)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            //  Navigation
            R.id.nav_main -> openIntent(this, "", MainActivity::class.java)
            R.id.nav_photo -> openIntent(this, "", CoffeeSnapsActivity::class.java)
        }

        binding.drawerLayout3.closeDrawer(GravityCompat.START)
        return true
    }
}