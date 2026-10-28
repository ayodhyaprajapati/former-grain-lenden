package com.farmer.grainlenden.screens
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.farmer.grainlenden.GrainAppViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MandiRatesScreen(viewModel: GrainAppViewModel) {
    val rates by viewModel.mandiRates.collectAsState()
    val loading by viewModel.isLoadingRates.collectAsState()
    var showDialog by remember { mutableStateOf(false) }
    var tempKey by remember { mutableStateOf(viewModel.customApiKey) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mandi Bhav (मंडी भाव)") },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF2E7D32), titleContentColor = Color.White, actionIconContentColor = Color.White),
                actions = {
                    IconButton(onClick = { viewModel.fetchMandiRates() }) { Icon(Icons.Default.Refresh, contentDescription = null) }
                    IconButton(onClick = { showDialog = true }) { Icon(Icons.Default.Settings, contentDescription = null) }
                }
            )
        }
    ) { p ->
        Box(Modifier.padding(p).fillMaxSize().background(Color(0xFFF4F6F4))) {
            if (loading) CircularProgressIndicator(Modifier.align(Alignment.Center), color = Color(0xFF2E7D32))
            else LazyColumn(contentPadding = PaddingValues(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(rates) { r ->
                    Card(colors = CardDefaults.cardColors(containerColor = Color.White), shape = RoundedCornerShape(10.dp)) {
                        Row(Modifier.fillMaxWidth().padding(14.dp), Arrangement.SpaceBetween, Alignment.CenterVertically) {
                            Column { Text(r.commodity, fontWeight = FontWeight.Bold, fontSize = 17.sp, color = Color(0xFF1B5E20)); Text("📍 ${r.market}, ${r.district}", fontSize = 12.sp, color = Color.Gray) }
                            Text("₹${r.modal_price}/Qtl", fontWeight = FontWeight.ExtraBold, fontSize = 18.sp, color = Color(0xFFD32F2F))
                        }
                    }
                }
            }
        }
        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                title = { Text("Configure Mandi API Key") },
                text = { OutlinedTextField(value = tempKey, onValueChange = { tempKey = it }, label = { Text("data.gov.in API Key") }, modifier = Modifier.fillMaxWidth()) },
                confirmButton = { Button(onClick = { viewModel.customApiKey = tempKey; viewModel.fetchMandiRates(); showDialog = false }) { Text("Save") } }
            )
        }
    }
}