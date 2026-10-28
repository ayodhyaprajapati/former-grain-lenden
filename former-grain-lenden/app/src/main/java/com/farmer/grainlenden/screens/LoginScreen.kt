package com.farmer.grainlenden.screens
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun LoginScreen(onLoginSuccess: () -> Unit) {
    val context = LocalContext.current
    var phone by remember { mutableStateOf("") }
    var otp by remember { mutableStateOf("") }
    var otpSent by remember { mutableStateOf(false) }

    Column(Modifier.fillMaxSize().background(Color(0xFFF7FBF4)).padding(24.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
        Text("🌾 Former Grain LenDen", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color(0xFF2E7D32))
        Text("किसान अनाज लेन-देन Portal", color = Color.Gray, modifier = Modifier.padding(bottom = 24.dp))
        Card(shape = RoundedCornerShape(12.dp), colors = CardDefaults.cardColors(containerColor = Color.White), modifier = Modifier.fillMaxWidth()) {
            Column(Modifier.padding(16.dp)) {
                OutlinedTextField(value = phone, onValueChange = { if(it.length<=10) phone = it }, label = { Text("Mobile Number") }, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number), modifier = Modifier.fillMaxWidth(), enabled = !otpSent)
                if (otpSent) {
                    Spacer(Modifier.height(10.dp))
                    OutlinedTextField(value = otp, onValueChange = { otp = it }, label = { Text("Enter OTP (Try: 123456)") }, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number), modifier = Modifier.fillMaxWidth())
                }
                Spacer(Modifier.height(16.dp))
                Button(onClick = {
                    if (!otpSent) {
                        if (phone.length == 10) { otpSent = true; Toast.makeText(context, "OTP Sent!", Toast.LENGTH_SHORT).show() }
                    } else { onLoginSuccess() }
                }, modifier = Modifier.fillMaxWidth(), colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2E7D32))) {
                    Text(if (!otpSent) "Send OTP" else "Verify & Continue")
                }
            }
        }
    }
}