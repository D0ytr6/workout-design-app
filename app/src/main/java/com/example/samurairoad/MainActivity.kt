package com.example.samurairoad

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.samurairoad.databinding.ActivityMainBinding
import com.example.samurairoad.ui.auth.AuthViewModel
import com.example.samurairoad.ui.auth.TokenViewModel
import com.example.samurairoad.ui.auth.models.SplashScreenStatus
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

// TODO read about activity life, fragment stack, is it live when fragment create and what is it doing in that moment
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private var isLoadDestination: Boolean = false

    private val tokenViewModel: TokenViewModel by viewModels()
    private val authViewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // not blocking call
        installSplashScreen().apply {
            setKeepVisibleCondition{
                tokenViewModel.splashScreenStatus.value == SplashScreenStatus.LOADING || !isLoadDestination
            }
        }

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // find fragment
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment

        navController = navHostFragment.navController
        Log.d("Splash", "nav controller init")

        // TODO check refresh token for expiration time if expired - update
        lifecycleScope.launch {
            tokenViewModel.splashScreenStatus.collect{ status ->
                if (status == SplashScreenStatus.LOGIN_SCREEN){
                    setStartGraphDestination(R.navigation.app_navigation, R.id.loginFragment)
                }
                else if (status == SplashScreenStatus.HOME_SCREEN){
                    setStartGraphDestination(R.navigation.app_navigation, R.id.homeFragment)
                }

                isLoadDestination = true
        } }


        val navView: BottomNavigationView = binding.bottomNavigation

        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.loginFragment) {
                navView.visibility = View.GONE
////                TODO will remove on whole app
//                supportActionBar?.hide()

            } else {
                navView.visibility = View.VISIBLE
            }

            // Passing each menu ID as a set of Ids because each
            // menu should be considered as top level destinations.
            navView.setupWithNavController(navController)

        }
    }

    private fun setStartGraphDestination(navigation: Int, startFragment: Int){
        val navGraph = navController.navInflater.inflate(navigation)
        navGraph.setStartDestination(startFragment)
        navController.graph = navGraph
    }

}