package com.example.skillfolio

/**
 * PortfolioRepository.kt
 *
 * Singleton sederhana sebagai penyimpanan data in-memory.
 * Data bertahan selama proses aplikasi hidup (tidak persisten ke disk).
 *
 * Dipakai oleh:
 *  - MainActivity           → tambahPengalaman()
 *  - TambahProjectActivity  → tambahProject()
 *  - ActivityListKegiatan   → semuaItem()
 */
object PortfolioRepository {

    private val _items: MutableList<PortfolioItem> = mutableListOf()

    /** Tambah satu item pengalaman */
    fun tambahPengalaman(kegiatan: Kegiatan) {
        _items.add(PortfolioItem.Pengalaman(kegiatan))
    }

    /** Tambah satu item project */
    fun tambahProject(project: Project) {
        _items.add(PortfolioItem.ProjectEntry(project))
    }

    /** Kembalikan snapshot list (tidak bisa dimodifikasi dari luar) */
    fun semuaItem(): List<PortfolioItem> = _items.toList()

    /** Jumlah total item */
    fun jumlah(): Int = _items.size
}
