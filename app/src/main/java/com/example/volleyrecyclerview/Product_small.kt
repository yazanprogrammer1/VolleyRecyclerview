package com.example.volleyrecyclerview

import org.json.JSONArray

data class Product_small(
    val id: Int,
    val title: String,
    val description: String,
    val price: Int,
    val images: JSONArray
)