package com.example.skillfolio

data class Kegiatan(

    // ── INFORMASI PRIBADI ──
    val namaLengkap: String,
    val tanggalLahir: String,
    val email: String,
    val noHp: String,
    val lokasi: String,

    // ── PENGALAMAN TERBARU ──
    val jabatan: String,
    val organisasi: String,
    val tahun: String,
    val deskripsi: String,

    // ── KEAHLIAN ──
    val keahlian: String        // Dipisah koma, contoh: "UI/UX, Kotlin, Figma"
)