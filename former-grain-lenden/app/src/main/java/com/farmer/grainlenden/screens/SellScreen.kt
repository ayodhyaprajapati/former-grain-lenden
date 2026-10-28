package com.farmer.grainlenden.screens
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.farmer.grainlenden.GrainAppViewModel
import com.farmer.grainlenden.SellPost

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SellScreen(viewModel: GrainAppViewModel) {
    val context = LocalContext.current
    var name by remember { mutableStateOf("") }
    var qty by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }

    Scaffold(topBar = { TopAppBar(title = { Text("Sell Grain (अनाज बेचें)") }, colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF2E7D32), titleContentColor = Color.White)) }) { p ->
        Column(Modifier.padding(p).fillMaxSize().background(Color(0xFFF4F6F4)).padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
            OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("Grain Name (e.g. Wheat / Chana)") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = qty, onValueChange = { qty = it }, label = { Text("Quantity in Quintals") }, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number), modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = price, onValueChange = { price = it }, label = { Text("Expected Price ₹ per Quintal") }, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number), modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = phone, onValueChange = { phone = it }, label = { Text("Farmer Mobile Number") }, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone), modifier = Modifier.fillMaxWidth())
            Button(onClick = {
                if (name.isNotBlank() && qty.isNotBlank() && price.isNotBlank()) {
                    viewModel.submitSellListing(SellPost(grainName = name, variety = "Standard", mandi = "Local Mandi", quantityQuintals = qty.toDoubleOrNull() ?: 10.0, expectedPricePerQuintal = price.toDoubleOrNull() ?: 2400.0, contactPhone = phone))
                    Toast.makeText(context, "Listing Published!", Toast.LENGTH_SHORT).show()
                    name = ""; qty = ""; price = ""; phone = ""
                }
            }, modifier = Modifier.fillMaxWidth().height(48.dp), colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2E7D32))) {
                Text("Publish Sell Offer", fontWeight = FontWeight.Bold)
            }
        }
    }
}