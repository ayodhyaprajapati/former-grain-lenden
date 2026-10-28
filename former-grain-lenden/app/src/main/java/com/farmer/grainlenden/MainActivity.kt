package com.farmer.grainlenden
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddBusiness
import androidx.compose.material.icons.filled.ListAlt
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.*
import com.farmer.grainlenden.screens.*

class MainActivity : ComponentActivity() {
    private val vm by viewModels<GrainAppViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { MaterialTheme { MainApp(vm) } }
    }
}

@Composable
fun MainApp(vm: GrainAppViewModel) {
    val nav = rememberNavController()
    var loggedIn by remember { mutableStateOf(false) }

    if (!loggedIn) {
        LoginScreen(onLoginSuccess = { loggedIn = true })
    } else {
        Scaffold(
            bottomBar = {
                NavigationBar(containerColor = Color.White) {
                    val entry by nav.currentBackStackEntryAsState()
                    val route = entry?.destination?.route
                    NavigationBarItem(selected = route == "rates", onClick = { nav.navigate("rates") }, icon = { Icon(Icons.Default.ListAlt, null) }, label = { Text("Rates") })
                    NavigationBarItem(selected = route == "buy", onClick = { nav.navigate("buy") }, icon = { Icon(Icons.Default.ShoppingCart, null) }, label = { Text("Buy") })
                    NavigationBarItem(selected = route == "sell", onClick = { nav.navigate("sell") }, icon = { Icon(Icons.Default.AddBusiness, null) }, label = { Text("Sell") })
                }
            }
        ) { p ->
            NavHost(nav, startDestination = "rates", modifier = Modifier.padding(p)) {
                composable("rates") { MandiRatesScreen(vm) }
                composable("buy") { BuyScreen(vm, onNavigateToCart = { nav.navigate("cart") }) }
                composable("sell") { SellScreen(vm) }
                composable("cart") { CartScreen(vm, onBack = { nav.popBackStack() }) }
            }
        }
    }
}