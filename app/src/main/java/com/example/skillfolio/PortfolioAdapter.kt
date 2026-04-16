package com.example.skillfolio

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

/**
 * PortfolioAdapter.kt
 *
 * Adapter tunggal untuk RecyclerView campuran yang menampilkan
 * Kegiatan (pengalaman) dan Project dalam satu daftar.
 *
 * Layout: item_portfolio.xml
 * Data  : List<PortfolioItem> (sealed class)
 */
class PortfolioAdapter(
    private val items: List<PortfolioItem>,
    private val onItemClick: (PortfolioItem) -> Unit
) : RecyclerView.Adapter<PortfolioAdapter.ViewHolder>() {

    // ─────────────────────────────────────────
    // ViewHolder — satu class untuk semua tipe
    // ─────────────────────────────────────────
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val viewAccentBar  : View     = itemView.findViewById(R.id.viewAccentBar)
        val tvItemNama     : TextView = itemView.findViewById(R.id.tvItemNama)
        val tvItemTahun    : TextView = itemView.findViewById(R.id.tvItemTahun)
        val tvItemSub1     : TextView = itemView.findViewById(R.id.tvItemSub1)
        val tvItemLink     : TextView = itemView.findViewById(R.id.tvItemLink)
        val tvItemDeskripsi: TextView = itemView.findViewById(R.id.tvItemDeskripsi)
        val tvItemInisial  : TextView = itemView.findViewById(R.id.tvItemInisial)
        val tvItemTipeLabel: TextView = itemView.findViewById(R.id.tvItemTipeLabel)
    }

    // ─────────────────────────────────────────
    // Inflate
    // ─────────────────────────────────────────
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_portfolio, parent, false)
        return ViewHolder(view)
    }

    // ─────────────────────────────────────────
    // Bind
    // ─────────────────────────────────────────
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (val item = items[position]) {

            // ── Kegiatan / Pengalaman ──────────────────────
            is PortfolioItem.Pengalaman -> {
                val data = item.data

                // Aksen kiri: emas
                holder.viewAccentBar.setBackgroundColor(COLOR_GOLD)

                // Judul = jabatan
                holder.tvItemNama.text = data.jabatan

                // Tahun — warna emas
                holder.tvItemTahun.text      = data.tahun
                holder.tvItemTahun.setTextColor(COLOR_GOLD)

                // Sub-info 1 = organisasi (abu muted)
                holder.tvItemSub1.text      = data.organisasi
                holder.tvItemSub1.setTextColor(COLOR_MUTED)
                holder.tvItemSub1.setTypeface(null, android.graphics.Typeface.NORMAL)

                // Link: tidak relevan untuk kegiatan → sembunyikan
                holder.tvItemLink.visibility = View.GONE

                // Deskripsi
                holder.tvItemDeskripsi.text = data.deskripsi

                // Inisial avatar + label tipe
                holder.tvItemInisial.text   = buatInisial(data.jabatan)
                holder.tvItemTipeLabel.text = "Pengalaman"

                // Click
                holder.itemView.setOnClickListener { onItemClick(item) }
            }

            // ── Project ────────────────────────────────────
            is PortfolioItem.ProjectEntry -> {
                val data = item.data

                // Aksen kiri: biru
                holder.viewAccentBar.setBackgroundColor(COLOR_BLUE)

                // Judul = nama project
                holder.tvItemNama.text = data.namaProject

                // Tahun — warna biru
                holder.tvItemTahun.text      = data.tahun
                holder.tvItemTahun.setTextColor(COLOR_BLUE)

                // Sub-info 1 = teknologi stack (emas bold)
                holder.tvItemSub1.text      = data.teknologi
                holder.tvItemSub1.setTextColor(COLOR_GOLD)
                holder.tvItemSub1.setTypeface(null, android.graphics.Typeface.BOLD)

                // Link GitHub (tampilkan jika ada)
                if (data.linkProject.isBlank()) {
                    holder.tvItemLink.visibility = View.GONE
                } else {
                    holder.tvItemLink.visibility = View.VISIBLE
                    holder.tvItemLink.text       = data.linkProject
                }

                // Deskripsi
                holder.tvItemDeskripsi.text = data.deskripsi

                // Inisial avatar + label tipe
                holder.tvItemInisial.text   = buatInisial(data.namaProject)
                holder.tvItemTipeLabel.text = "Project"

                // Click
                holder.itemView.setOnClickListener { onItemClick(item) }
            }
        }
    }

    override fun getItemCount(): Int = items.size

    // ─────────────────────────────────────────
    // Helpers
    // ─────────────────────────────────────────

    /** Ambil 1–2 huruf kapital pertama untuk avatar inisial */
    private fun buatInisial(teks: String): String {
        val parts = teks.trim().split(" ").filter { it.isNotEmpty() }
        return when {
            parts.isEmpty() -> "?"
            parts.size == 1 -> parts[0].take(2).uppercase()
            else            -> "${parts[0].first()}${parts[1].first()}".uppercase()
        }
    }

    companion object {
        private val COLOR_GOLD  = Color.parseColor("#FFD700")
        private val COLOR_BLUE  = Color.parseColor("#4A90D9")
        private val COLOR_MUTED = Color.parseColor("#9AA1B5")
    }
}
