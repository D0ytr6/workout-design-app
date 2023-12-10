package com.example.samurairoad

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.samurairoad.databinding.ActivityMainBinding
import com.example.samurairoad.room.WorkoutDatabase
import com.example.samurairoad.room.tables.WorkoutTableModel
import com.example.samurairoad.ui.auth.TokenViewModel
import com.example.samurairoad.ui.auth.UserViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val tokenViewModel: TokenViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navController = findNavController(R.id.nav_host_fragment_activity_main)

//        tokenViewModel.tokenLiveData.observe(this) {
//            if (it != null){
//                navController.navigate(R.id.homeFragment)
//            }
//            else{
//                navController.navigate(R.id.loginFragment)
//            }
//        }

        val navView: BottomNavigationView = binding.bottomNavigation

        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.loginFragment) {
                navView.visibility = View.GONE
//                TODO will remove on whole app
                supportActionBar?.hide()

            } else {
                navView.visibility = View.VISIBLE
            }

            // Passing each menu ID as a set of Ids because each
            // menu should be considered as top level destinations.
            navView.setupWithNavController(navController)

        }
    }

}