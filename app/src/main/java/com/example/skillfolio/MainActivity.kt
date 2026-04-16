package com.example.skillfolio

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.skillfolio.databinding.ActivityMainBinding

/**
 * MainActivity — Form input Tab: Pengalaman
 *
 * Alur:
 *  Launch → MainActivity (tab Pengalaman aktif)
 *  tab Project klik → TambahProjectActivity
 *  btnSimpan → simpan ke PortfolioRepository → finish() ke ActivityListKegiatan
 *  btnLihatSemua → ActivityListKegiatan
 */
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var tabAktif = TAB_PENGALAMAN

    companion object {
        const val TAB_PENGALAMAN = 0
        const val TAB_PROJECT    = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupTabs()
        setupListeners()
    }

    // ─────────────────────────────────────────
    // Tab switching
    // ─────────────────────────────────────────
    private fun setupTabs() {
        pilihTab(TAB_PENGALAMAN)

        binding.tabPengalaman.setOnClickListener { pilihTab(TAB_PENGALAMAN) }

        // Tab Project → langsung buka TambahProjectActivity
        binding.tabProject.setOnClickListener {
            startActivity(Intent(this, TambahProjectActivity::class.java))
        }
    }

    private fun pilihTab(tab: Int) {
        tabAktif = tab
        setTabAktif(binding.tabPengalaman, tab == TAB_PENGALAMAN)
        setTabAktif(binding.tabProject,    tab == TAB_PROJECT)
        binding.formPengalaman.visibility = if (tab == TAB_PENGALAMAN) View.VISIBLE else View.GONE
        binding.formProject.visibility    = if (tab == TAB_PROJECT)    View.VISIBLE else View.GONE
    }

    private fun setTabAktif(tv: TextView, aktif: Boolean) {
        tv.setTextColor(if (aktif) 0xFFFFD700.toInt() else 0xFF6B7289.toInt())
        tv.setTypeface(null, if (aktif) android.graphics.Typeface.BOLD else android.graphics.Typeface.NORMAL)
        tv.setBackgroundResource(if (aktif) R.drawable.tab_selected_indicator else 0)
    }

    // ─────────────────────────────────────────
    // Listeners
    // ─────────────────────────────────────────
    private fun setupListeners() {

        // ── Simpan Pengalaman → simpan ke PortfolioRepository ────────────
        binding.btnSimpan.setOnClickListener {
            val jabatan    = binding.etJabatan.text.toString().trim()
            val organisasi = binding.etOrganisasi.text.toString().trim()
            val tahun      = binding.etTahun.text.toString().trim()
            val deskripsi  = binding.etDeskripsi.text.toString().trim()

            when {
                jabatan.isEmpty() -> {
                    binding.etJabatan.error = "Jabatan tidak boleh kosong"
                    binding.etJabatan.requestFocus()
                }
                organisasi.isEmpty() -> {
                    binding.etOrganisasi.error = "Nama organisasi tidak boleh kosong"
                    binding.etOrganisasi.requestFocus()
                }
                tahun.isEmpty() -> {
                    binding.etTahun.error = "Tahun tidak boleh kosong"
                    binding.etTahun.requestFocus()
                }
                deskripsi.isEmpty() -> {
                    binding.etDeskripsi.error = "Deskripsi tidak boleh kosong"
                    binding.etDeskripsi.requestFocus()
                }
                else -> {
                    // Simpan ke repository
                    PortfolioRepository.tambahPengalaman(
                        Kegiatan(
                            namaLengkap  = jabatan,   // judul utama = jabatan
                            tanggalLahir = "-",
                            email        = "-",
                            noHp         = "-",
                            lokasi       = "-",
                            jabatan      = jabatan,
                            organisasi   = organisasi,
                            tahun        = tahun,
                            deskripsi    = deskripsi,
                            keahlian     = "-"
                        )
                    )

                    Toast.makeText(this, "Pengalaman \"$jabatan\" tersimpan!", Toast.LENGTH_SHORT).show()

                    // Reset form
                    binding.etJabatan.text?.clear()
                    binding.etOrganisasi.text?.clear()
                    binding.etTahun.text?.clear()
                    binding.etDeskripsi.text?.clear()

                    // Kembali ke list (atau buka list jika belum ada di stack)
                    val intent = Intent(this, ActivityListKegiatan::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                    startActivity(intent)
                }
            }
        }

        // ── Simpan Project (form di tab 2 — fallback jika tab di-switch manual) ──
        binding.btnSimpanProject.setOnClickListener {
            startActivity(Intent(this, TambahProjectActivity::class.java))
        }

        // ── Lihat Semua → ActivityListKegiatan ───────────────────────────
        binding.btnLihatSemua.setOnClickListener {
            startActivity(Intent(this, ActivityListKegiatan::class.java))
        }
    }
}
