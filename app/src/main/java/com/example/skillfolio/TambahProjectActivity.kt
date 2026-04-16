package com.example.skillfolio

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.skillfolio.databinding.ActivityTambahProjectBinding

/**
 * TambahProjectActivity.kt
 *
 * Halaman tambah project baru.
 * Layout: activity_tambah_project.xml
 *
 * Alur:
 *  ActivityListKegiatan.fabTambahKegiatan → pilih Project tab
 *  → TambahProjectActivity
 *  → simpan ke PortfolioRepository
 *  → finish() → kembali ke ActivityListKegiatan (yang refresh di onResume)
 */
class TambahProjectActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTambahProjectBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTambahProjectBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // ── Tombol Back ──────────────────────────────────────────────────
        binding.btnBackProject.setOnClickListener { finish() }

        // ── Tab: klik "Pengalaman" → kembali ke MainActivity ────────────
        binding.tabPengalaman.setOnClickListener {
            finish()   // MainActivity sudah di back stack
        }

        // ── Simpan Project ───────────────────────────────────────────────
        binding.btnSimpanProject.setOnClickListener {
            simpanProject()
        }
    }

    private fun simpanProject() {
        val namaProject  = binding.etNamaProject.text.toString().trim()
        val teknologi    = binding.etTeknologi.text.toString().trim()
        val tahun        = binding.etTahunProject.text.toString().trim()
        val link         = binding.etLinkProject.text.toString().trim()
        val deskripsi    = binding.etDeskripsiProject.text.toString().trim()

        // ── Validasi ─────────────────────────────────────────────────────
        when {
            namaProject.isEmpty() -> {
                binding.etNamaProject.error = "Nama project tidak boleh kosong"
                binding.etNamaProject.requestFocus()
                return
            }
            teknologi.isEmpty() -> {
                binding.etTeknologi.error = "Teknologi tidak boleh kosong"
                binding.etTeknologi.requestFocus()
                return
            }
            deskripsi.isEmpty() -> {
                binding.etDeskripsiProject.error = "Deskripsi tidak boleh kosong"
                binding.etDeskripsiProject.requestFocus()
                return
            }
        }

        // ── Simpan ke repository ──────────────────────────────────────────
        PortfolioRepository.tambahProject(
            Project(
                namaProject  = namaProject,
                teknologi    = teknologi,
                tahun        = tahun.ifBlank { "-" },
                linkProject  = link,
                deskripsi    = deskripsi
            )
        )

        Toast.makeText(this, "Project \"$namaProject\" tersimpan!", Toast.LENGTH_SHORT).show()
        finish()   // kembali ke ActivityListKegiatan → onResume akan refresh list
    }
}
