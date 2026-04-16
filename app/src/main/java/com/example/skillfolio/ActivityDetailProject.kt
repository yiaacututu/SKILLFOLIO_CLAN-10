package com.example.skillfolio

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton

/**
 * ActivityDetailProject.kt
 *
 * Halaman detail untuk satu item Project.
 * Menerima data via Intent extras dari ActivityListKegiatan.
 * Layout: activity_detail_project.xml
 */
class ActivityDetailProject : AppCompatActivity() {

    companion object {
        const val EXTRA_NAMA_PROJECT = "extra_nama_project"
        const val EXTRA_TEKNOLOGI    = "extra_teknologi"
        const val EXTRA_TAHUN        = "extra_tahun_project"
        const val EXTRA_LINK         = "extra_link_project"
        const val EXTRA_DESKRIPSI    = "extra_deskripsi_project"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_project)

        // ── Terima data dari Intent ──────────────────────────────────────
        val namaProject = intent.getStringExtra(EXTRA_NAMA_PROJECT) ?: "-"
        val teknologi   = intent.getStringExtra(EXTRA_TEKNOLOGI)    ?: "-"
        val tahun       = intent.getStringExtra(EXTRA_TAHUN)        ?: "-"
        val link        = intent.getStringExtra(EXTRA_LINK)         ?: ""
        val deskripsi   = intent.getStringExtra(EXTRA_DESKRIPSI)    ?: "-"

        // ── Isi Hero Card ────────────────────────────────────────────────
        val inisial = buatInisial(namaProject)
        findViewById<TextView>(R.id.tvDetailProjectInisial).text           = inisial
        findViewById<TextView>(R.id.tvDetailProjectNama).text              = namaProject
        findViewById<TextView>(R.id.tvDetailProjectTeknologiSingkat).text  = teknologi
        findViewById<TextView>(R.id.tvDetailProjectTahun).text             = tahun

        // ── Isi Detail Rows ──────────────────────────────────────────────
        findViewById<TextView>(R.id.tvDetailProjectNamaRow).text    = namaProject
        findViewById<TextView>(R.id.tvDetailProjectTeknologi).text  = teknologi
        findViewById<TextView>(R.id.tvDetailProjectTahunRow).text   = tahun
        findViewById<TextView>(R.id.tvDetailProjectLink).text       = link.ifBlank { "Tidak ada link" }
        findViewById<TextView>(R.id.tvDetailProjectDeskripsi).text  = deskripsi

        // ── Tombol Back ──────────────────────────────────────────────────
        findViewById<android.widget.ImageButton>(R.id.btnBackDetailProject)
            .setOnClickListener { finish() }

        // ── Tombol Buka Link (top bar) ───────────────────────────────────
        val bukaLink: (Unit) -> Unit = {
            if (link.isNotBlank()) {
                val url = if (link.startsWith("http")) link else "https://$link"
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
            }
        }

        val tglBtnLink = link.isNotBlank()
        val btnLinkTop = findViewById<AppCompatButton>(R.id.btnBukaLink)
        btnLinkTop.alpha = if (tglBtnLink) 1f else 0.4f
        btnLinkTop.setOnClickListener { bukaLink(Unit) }

        // ── Tombol Buka Link (bottom) ────────────────────────────────────
        val btnLinkBottom = findViewById<AppCompatButton>(R.id.btnBukaLinkBottom)
        btnLinkBottom.alpha = if (tglBtnLink) 1f else 0.4f
        btnLinkBottom.setOnClickListener { bukaLink(Unit) }
    }

    /** Ambil 1–2 huruf kapital pertama dari nama project untuk avatar */
    private fun buatInisial(nama: String): String {
        val parts = nama.trim().split(" ").filter { it.isNotEmpty() }
        return when {
            parts.isEmpty() -> "?"
            parts.size == 1 -> parts[0].take(2).uppercase()
            else            -> "${parts[0].first()}${parts[1].first()}".uppercase()
        }
    }
}
