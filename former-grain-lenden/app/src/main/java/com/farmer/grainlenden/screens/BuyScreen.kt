package com.farmer.grainlenden.screens
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.farmer.grainlenden.GrainAppViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BuyScreen(viewModel: GrainAppViewModel, onNavigateToCart: () -> Unit) {
    val products by viewModel.marketProducts.collectAsState()
    val cart by viewModel.cart.collectAsState()
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Buy Grains") },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF2E7D32), titleContentColor = Color.White),
                actions = {
                    BadgedBox(badge = { if (cart.isNotEmpty()) Badge { Text(cart.size.toString()) } }, modifier = Modifier.padding(end = 12.dp)) {
                        IconButton(onClick = onNavigateToCart) { Icon(Icons.Default.ShoppingCart, contentDescription = null, tint = Color.White) }
                    }
                }
            )
        }
    ) { p ->
        LazyColumn(Modifier.padding(p).fillMaxSize().background(Color(0xFFF4F6F4)), contentPadding = PaddingValues(12.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
            items(products) { item ->
                var qty by remember { mutableStateOf(5.0) }
                Card(colors = CardDefaults.cardColors(containerColor = Color.White), shape = RoundedCornerShape(10.dp)) {
                    Column(Modifier.padding(14.dp)) {
                        Text(item.nameEnglish, fontWeight = FontWeight.Bold, fontSize = 17.sp, color = Color(0xFF1B5E20))
                        Text("Mandi: ${item.mandiName} | Rate: ₹${item.pricePerQuintal.toInt()}/Qtl", fontSize = 13.sp)
                        Spacer(Modifier.height(8.dp))
                        Row(Modifier.fillMaxWidth(), Arrangement.SpaceBetween, Alignment.CenterVertically) {
                            Text("Qty: ${qty.toInt()} Qtl", fontWeight = FontWeight.SemiBold)
                            Button(onClick = { viewModel.addToCart(item, qty); Toast.makeText(context, "Added!", Toast.LENGTH_SHORT).show() }, colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2E7D32))) { Text("Add to Cart") }
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(viewModel: GrainAppViewModel, onBack: () -> Unit) {
    val cart by viewModel.cart.collectAsState()
    var paid by remember { mutableStateOf(false) }
    val total = cart.sumOf { it.product.pricePerQuintal * it.quantityQuintals }

    Scaffold(topBar = { TopAppBar(title = { Text("Cart Checkout") }, colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF2E7D32), titleContentColor = Color.White)) }) { p ->
        Column(Modifier.padding(p).fillMaxSize().padding(14.dp)) {
            LazyColumn(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(cart) { item ->
                    Card(colors = CardDefaults.cardColors(containerColor = Color.White)) {
                        Row(Modifier.fillMaxWidth().padding(12.dp), Arrangement.SpaceBetween, Alignment.CenterVertically) {
                            Column { Text(item.product.nameEnglish, fontWeight = FontWeight.Bold); Text("${item.quantityQuintals} Qtl × ₹${item.product.pricePerQuintal.toInt()}") }
                            IconButton(onClick = { viewModel.removeFromCart(item) }) { Icon(Icons.Default.Delete, contentDescription = null, tint = Color.Red) }
                        }
                    }
                }
            }
            Text("Total: ₹${total.toInt()}", fontWeight = FontWeight.Bold, fontSize = 20.sp, color = Color(0xFF1B5E20))
            Spacer(Modifier.height(10.dp))
            Button(onClick = { paid = true }, modifier = Modifier.fillMaxWidth().height(48.dp), colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2E7D32))) { Text("Pay via UPI / Escrow") }
        }
        if (paid) {
            AlertDialog(onDismissRequest = {}, title = { Text("Payment Confirmed! ✅") }, text = { Text("Order placed for ₹${total.toInt()}") }, confirmButton = { Button(onClick = { viewModel.clearCart(); paid = false; onBack() }) { Text("Done") } })
        }
    }
}