package com.example.prog7313_starsucks

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import com.example.prog7313_starsucks.databinding.ActivityOrderDetailsBinding
import com.google.android.material.navigation.NavigationView

class OrderDetailsActivity: AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    var order = Order()
    private lateinit var binding: ActivityOrderDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // using data binding to inflate the 'activity_order_details' screen
        binding = ActivityOrderDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.navToolbar3)

        var toggleOnOff = ActionBarDrawerToggle(
            this,
            binding.drawerLayout3, binding.navToolbar3,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        binding.drawerLayout3.addDrawerListener(toggleOnOff)
        toggleOnOff.syncState()

        // bringToFront: bring a view to the front of the view hierarchy, making it
        //visually appear on top of other views. This is to make sure that it is visible
        // and not obscured by other views
        binding.navView3.bringToFront()
        // Setting the Item Selected Listener
        binding.navView3.setNavigationItemSelectedListener(this)

        // get the name of the ordered product from the intent
        order.productName = intent.getStringExtra("order").toString()

        // set the product name on the text view
        binding.txtPlacedOrder.text = order.productName

        // changing image based on what the customer picked
        when (order.productName) {
            "Soy Latte" -> binding.imgOrderedDrink.setImageResource(R.drawable.sb1)
            "Chocco Frapp" -> binding.imgOrderedDrink.setImageResource(R.drawable.sb2)
            "Bottled Americano" -> binding.imgOrderedDrink.setImageResource(R.drawable.sb3)
            "Rainbow Frapp" -> binding.imgOrderedDrink.setImageResource(R.drawable.sb4)
            "Caramel Frapp" -> binding.imgOrderedDrink.setImageResource(R.drawable.sb5)
            "Black Forest Frapp" -> binding.imgOrderedDrink.setImageResource(R.drawable.sb6)
        }

        binding.fab.setOnClickListener() {
            shareIntent(this, order.productName)
        }

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