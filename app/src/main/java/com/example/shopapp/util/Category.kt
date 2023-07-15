package com.example.shopapp.util

enum class Category(val id: String, val title: String) {
    All("all","All"),
    Men("men's clothing","Men's clothing"),
    Women("women's clothing","Women's clothing"),
    Jewelery("jewelery","Jewelery"),
    Electronics("electronics","Electronics")
}

fun getCategory(): List<Category> {
    return listOf(
        Category.All,
        Category.Men,
        Category.Women,
        Category.Jewelery,
        Category.Electronics
    )
}