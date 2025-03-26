package com.example.prog7313_starsucks

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import com.example.prog7313_starsucks.databinding.ActivityMainWithNavDrawerBinding
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity(), View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {
    var order = Order()
    private lateinit var binding: ActivityMainWithNavDrawerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainWithNavDrawerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.navToolbar)

        binding.imgSb1.setOnClickListener(this)
        binding.imgSb2.setOnClickListener(this)
        binding.imgSb3.setOnClickListener(this)
        binding.imgSb4.setOnClickListener(this)
        binding.imgSb5.setOnClickListener(this)
        binding.imgSb6.setOnClickListener(this)


        var toggleOnOff = ActionBarDrawerToggle(
            this,
            binding.drawerLayout, binding.navToolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        binding.drawerLayout.addDrawerListener(toggleOnOff)
        toggleOnOff.syncState()

        // bringToFront: bring a view to the front of the view hierarchy, making it
        //visually appear on top of other views. This is to make sure that it is visible
        // and not obscured by other views
        binding.navView.bringToFront()
        // Setting the Item Selected Listener
        binding.navView.setNavigationItemSelectedListener(this)

    }

    // The onClick method will override the onCreate method whenever a view
    // has been clicked.
    override fun onClick(v: View?) {

        // Determine which view has been clicked based on ID
        when (v?.id) {
            R.id.img_sb1 -> order.productName = "Soy Latte"
            R.id.img_sb2 -> order.productName = "Chocco Frapp"
            R.id.img_sb3 -> order.productName = "Bottled Americano"
            R.id.img_sb4 -> order.productName = "Rainbow Frapp"
            R.id.img_sb5 -> order.productName = "Caramel Frapp"
            R.id.img_sb6 -> order.productName = "Black Forest Frapp"
        }

        Toast.makeText(this, "MMM " + order.productName, Toast.LENGTH_SHORT).show()
        openIntent(applicationContext, order.productName, OrderDetailsActivity::class.java)

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            //  Navigate to the photo activity
            R.id.nav_main -> openIntent(this, "", MainActivity::class.java)
            R.id.nav_photo -> openIntent(this, "", CoffeeSnapsActivity::class.java)
            // R.id.nav_history -> openItent(this, "", ::class.java)
        }

        binding.drawerLayout.closeDrawer(GravityCompat.START)

        return true
    }
}