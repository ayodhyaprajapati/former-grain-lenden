package com.farmer.grainlenden
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class GrainAppViewModel : ViewModel() {
    private val apiService = MandiApiService.create()
    var customApiKey: String = "579b464db66ec23bdd000001cdd3946e44ce4aad7209ff7b23ac571b"
    private val _mandiRates = MutableStateFlow<List<MandiRecord>>(emptyList())
    val mandiRates = _mandiRates.asStateFlow()
    private val _isLoadingRates = MutableStateFlow(false)
    val isLoadingRates = _isLoadingRates.asStateFlow()
    private val _marketProducts = MutableStateFlow<List<GrainProduct>>(emptyList())
    val marketProducts = _marketProducts.asStateFlow()
    private val _cart = MutableStateFlow<List<CartItem>>(emptyList())
    val cart = _cart.asStateFlow()

    init {
        _marketProducts.value = listOf(
            GrainProduct(nameHindi = "गेहूं", nameEnglish = "Wheat (Sharbati)", variety = "Grade A", mandiName = "Indore Mandi, MP", pricePerQuintal = 2450.0, availableQuintals = 80.0, sellerName = "Ramesh Patel", sellerPhone = "+919876543210"),
            GrainProduct(nameHindi = "चना", nameEnglish = "Chana (Bengal Gram)", variety = "Desi", mandiName = "Kota Mandi, RJ", pricePerQuintal = 5850.0, availableQuintals = 45.0, sellerName = "Suresh Meena", sellerPhone = "+919876543211"),
            GrainProduct(nameHindi = "धान", nameEnglish = "Paddy / Rice (Basmati)", variety = "1121 Pusa", mandiName = "Karnal Mandi, HR", pricePerQuintal = 3800.0, availableQuintals = 120.0, sellerName = "Gurpreet Singh", sellerPhone = "+919876543212")
        )
        fetchMandiRates()
    }
    fun fetchMandiRates() {
        viewModelScope.launch {
            _isLoadingRates.value = true
            try {
                val res = apiService.getLiveMandiPrices(apiKey = customApiKey)
                _mandiRates.value = res.records ?: emptyList()
            } catch (e: Exception) {
                _mandiRates.value = listOf(MandiRecord("Madhya Pradesh", "Indore", "Indore Mandi", "Wheat", "2480", "Today"), MandiRecord("Rajasthan", "Kota", "Kota Mandi", "Chana", "5920", "Today"))
            } finally { _isLoadingRates.value = false }
        }
    }
    fun addToCart(product: GrainProduct, qtl: Double) { _cart.value = _cart.value + CartItem(product, qtl) }
    fun removeFromCart(item: CartItem) { _cart.value = _cart.value.filter { it.product.id != item.product.id } }
    fun clearCart() { _cart.value = emptyList() }
    fun submitSellListing(l: SellPost) {
        _marketProducts.value = listOf(GrainProduct(nameHindi = l.grainName, nameEnglish = l.grainName, variety = l.variety, mandiName = l.mandi, pricePerQuintal = l.expectedPricePerQuintal, availableQuintals = l.quantityQuintals, sellerName = "My Crop", sellerPhone = l.contactPhone)) + _marketProducts.value
    }
}