package com.example.skillfolio

data class Project(
    val namaProject: String,
    val teknologi: String,       // Dipisah koma, contoh: "Kotlin, Firebase, Figma"
    val tahun: String,
    val linkProject: String,     // GitHub / URL
    val deskripsi: String
)
