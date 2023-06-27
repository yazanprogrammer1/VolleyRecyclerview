package com.example.volleyrecyclerview

data class productsGenerated(
    val limit: Int,
    val products: List<Product>,
    val skip: Int,
    val total: Int
)