package com.example.sueta

import android.graphics.Bitmap

data class CreateMenuData(
    var cardName : String,
    var userName : String,
    var blood : String,
    var allergy : String,
    var diagnoses : String,
    var medicines : String,
    var contacts : String,
    var ps : String,
    var qrCode : Bitmap? = null
)
