package com.example.teacher_kotlin_app

import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import com.example.teacher_kotlin_app.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mDbRef: DatabaseReference

    companion object {
        var name: String = ""
        var email: String = ""
        var uid: String = ""
        var role: String = ""


    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)

        mAuth = FirebaseAuth.getInstance()
        mDbRef = FirebaseDatabase.getInstance().reference


        val user = mAuth.currentUser

        mDbRef.child("user").child(uid).child("name").get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                name = (task.result?.value as? String).toString()
            }
        }

        email = user?.email ?: ""
        uid = user?.uid ?:""
        mDbRef.child("user").child(uid).child("role").get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                role = (task.result?.value as? String).toString()
            }
        }


        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val headerView: View = navView.getHeaderView(0)
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        mAuth = FirebaseAuth.getInstance()

        val userEmail: TextView = headerView.findViewById(R.id.headerEmail)
        if (user != null) {
            mAuth.currentUser?.let {
                userEmail.text = it.email
            }
        }

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_post,R.id.nav_add, R.id.nav_logout,
            ), drawerLayout
        )

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

}