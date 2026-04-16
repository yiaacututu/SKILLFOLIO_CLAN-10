package com.example.skillfolio

import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton

/**
 * ActivityDetailKegiatan.kt
 *
 * ID yang tersedia di activity_detail_kegiatan.xml:
 *  Hero card : tvDetailInisial, tvDetailJabatan, tvDetailOrganisasi, tvDetailTahun
 *  Detail row: tvDetailJabatanRow, tvDetailOrganisasiRow, tvDetailTahunRow, tvDetailDeskripsi
 *  Tombol    : btnBack, btnEksporCV (top), btnEksporCVBottom (bottom)
 */
class ActivityDetailKegiatan : AppCompatActivity() {

    companion object {
        const val EXTRA_NAMA          = "extra_nama"
        const val EXTRA_TANGGAL_LAHIR = "extra_tanggal_lahir"
        const val EXTRA_EMAIL         = "extra_email"
        const val EXTRA_NO_HP         = "extra_no_hp"
        const val EXTRA_LOKASI        = "extra_lokasi"
        const val EXTRA_POSISI        = "extra_posisi"
        const val EXTRA_PERUSAHAAN    = "extra_perusahaan"
        const val EXTRA_TAHUN         = "extra_tahun"
        const val EXTRA_DESKRIPSI     = "extra_deskripsi"
        const val EXTRA_KEAHLIAN      = "extra_keahlian"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_kegiatan)

        // ── Terima data dari Intent ──────────────────────────────────────
        val posisi      = intent.getStringExtra(EXTRA_POSISI)     ?: "-"
        val perusahaan  = intent.getStringExtra(EXTRA_PERUSAHAAN) ?: "-"
        val tahun       = intent.getStringExtra(EXTRA_TAHUN)      ?: "-"
        val deskripsi   = intent.getStringExtra(EXTRA_DESKRIPSI)  ?: "-"
        // Field tambahan (diterima tapi tidak ditampilkan di layout ini)
        val nama        = intent.getStringExtra(EXTRA_NAMA)       ?: posisi

        // ── Hero Card ────────────────────────────────────────────────────
        findViewById<TextView>(R.id.tvDetailInisial).text    = buatInisial(posisi)
        findViewById<TextView>(R.id.tvDetailJabatan).text    = posisi
        findViewById<TextView>(R.id.tvDetailOrganisasi).text = perusahaan
        findViewById<TextView>(R.id.tvDetailTahun).text      = tahun

        // ── Detail Rows ──────────────────────────────────────────────────
        findViewById<TextView>(R.id.tvDetailJabatanRow).text    = posisi
        findViewById<TextView>(R.id.tvDetailOrganisasiRow).text = perusahaan
        findViewById<TextView>(R.id.tvDetailTahunRow).text      = tahun
        findViewById<TextView>(R.id.tvDetailDeskripsi).text     = deskripsi

        // ── Tombol Back ──────────────────────────────────────────────────
        findViewById<ImageButton>(R.id.btnBack).setOnClickListener { finish() }

        // ── Tombol Ekspor CV ─────────────────────────────────────────────
        val eksporClick = android.view.View.OnClickListener {
            Toast.makeText(
                this,
                "Fitur Ekspor CV segera hadir!",
                Toast.LENGTH_SHORT
            ).show()
        }
        findViewById<AppCompatButton>(R.id.btnEksporCV).setOnClickListener(eksporClick)
        findViewById<AppCompatButton>(R.id.btnEksporCVBottom).setOnClickListener(eksporClick)
    }

    private fun buatInisial(teks: String): String {
        val parts = teks.trim().split(" ").filter { it.isNotEmpty() }
        return when {
            parts.isEmpty() -> "?"
            parts.size == 1 -> parts[0].take(2).uppercase()
            else            -> "${parts[0].first()}${parts[1].first()}".uppercase()
        }
    }
}
