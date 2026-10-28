package com.farmer.grainlenden
import java.util.UUID

data class MandiApiResponse(val records: List<MandiRecord>?)
data class MandiRecord(val state: String = "", val district: String = "", val market: String = "", val commodity: String = "", val modal_price: String = "", val arrival_date: String = "")
data class GrainProduct(val id: String = UUID.randomUUID().toString(), val nameHindi: String, val nameEnglish: String, val variety: String, val mandiName: String, val pricePerQuintal: Double, val availableQuintals: Double, val sellerName: String, val sellerPhone: String)
data class CartItem(val product: GrainProduct, var quantityQuintals: Double)
data class SellPost(val grainName: String, val variety: String, val mandi: String, val quantityQuintals: Double, val expectedPricePerQuintal: Double, val contactPhone: String)