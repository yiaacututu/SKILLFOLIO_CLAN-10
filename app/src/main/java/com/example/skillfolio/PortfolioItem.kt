package com.example.skillfolio

/**
 * PortfolioItem.kt
 *
 * Sealed class sebagai "amplop" data campuran untuk RecyclerView portfolio.
 * Satu list bisa memuat Pengalaman dan ProjectEntry sekaligus.
 */
sealed class PortfolioItem {

    /** Item tipe Kegiatan / Pengalaman Organisasi */
    data class Pengalaman(val data: Kegiatan) : PortfolioItem()

    /** Item tipe Project */
    data class ProjectEntry(val data: Project) : PortfolioItem()
}
