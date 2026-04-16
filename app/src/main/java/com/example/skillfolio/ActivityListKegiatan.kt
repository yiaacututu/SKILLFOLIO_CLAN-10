package com.example.skillfolio

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.View

/**
 * ActivityListKegiatan.kt — Hub utama portofolio user.
 *
 * Menampilkan semua Pengalaman + Project dari PortfolioRepository
 * dalam satu RecyclerView campuran.
 *
 * Refresh otomatis di onResume() setiap kali kembali dari:
 *  - MainActivity (tambah pengalaman)
 *  - TambahProjectActivity (tambah project)
 *
 * Navigasi:
 *  FAB "+ Tambah" → MainActivity
 *  Klik item Pengalaman → ActivityDetailKegiatan
 *  Klik item Project    → ActivityDetailProject
 */
class ActivityListKegiatan : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: PortfolioAdapter
    private lateinit var tvJumlah: TextView
    private lateinit var layoutEmpty: View
    private lateinit var etCari: EditText

    // List lengkap dari repository (diperbarui di onResume)
    private var semuaItems: List<PortfolioItem> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_kegiatan)

        // ── Inisialisasi view ─────────────────────────────────────────────
        recyclerView  = findViewById(R.id.recyclerViewKegiatan)
        tvJumlah      = findViewById(R.id.tvJumlahKegiatan)
        layoutEmpty   = findViewById(R.id.layoutEmpty)
        etCari        = findViewById(R.id.etCariKegiatan)

        recyclerView.layoutManager = LinearLayoutManager(this)

        // ── Adapter kosong dulu, diisi di onResume ───────────────────────
        adapter = buatAdapter(emptyList())
        recyclerView.adapter = adapter

        // ── FAB: buka MainActivity (form tambah pengalaman) ──────────────
        findViewById<com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton>(
            R.id.fabTambahKegiatan
        ).setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

        // ── Ekspor CV (placeholder) ──────────────────────────────────────
        findViewById<androidx.appcompat.widget.AppCompatButton>(R.id.btnEksporCV)
            .setOnClickListener {
                android.widget.Toast.makeText(
                    this, "Fitur Ekspor CV segera hadir!", android.widget.Toast.LENGTH_SHORT
                ).show()
            }

        // ── Search / filter ──────────────────────────────────────────────
        etCari.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, st: Int, c: Int, a: Int) {}
            override fun onTextChanged(s: CharSequence?, st: Int, b: Int, c: Int) {
                filterList(s.toString())
            }
            override fun afterTextChanged(s: Editable?) {}
        })
    }

    // ─────────────────────────────────────────────────────────────────────
    // onResume — refresh setiap kali kembali ke halaman ini
    // ─────────────────────────────────────────────────────────────────────
    override fun onResume() {
        super.onResume()
        semuaItems = PortfolioRepository.semuaItem()
        filterList(etCari.text.toString())
        updateBadge(semuaItems.size)
    }

    // ─────────────────────────────────────────────────────────────────────
    // Filter list berdasarkan query search
    // ─────────────────────────────────────────────────────────────────────
    private fun filterList(query: String) {
        val filtered = if (query.isBlank()) {
            semuaItems
        } else {
            val q = query.lowercase()
            semuaItems.filter { item ->
                when (item) {
                    is PortfolioItem.Pengalaman   ->
                        item.data.jabatan.lowercase().contains(q) ||
                        item.data.organisasi.lowercase().contains(q) ||
                        item.data.tahun.lowercase().contains(q) ||
                        item.data.deskripsi.lowercase().contains(q)

                    is PortfolioItem.ProjectEntry ->
                        item.data.namaProject.lowercase().contains(q) ||
                        item.data.teknologi.lowercase().contains(q) ||
                        item.data.tahun.lowercase().contains(q) ||
                        item.data.deskripsi.lowercase().contains(q)
                }
            }
        }

        // Rebuild adapter dengan list yang sudah difilter
        adapter = buatAdapter(filtered)
        recyclerView.adapter = adapter

        // Tampilkan/sembunyikan empty state
        layoutEmpty.visibility  = if (filtered.isEmpty()) View.VISIBLE else View.GONE
        recyclerView.visibility = if (filtered.isEmpty()) View.GONE   else View.VISIBLE
    }

    // ─────────────────────────────────────────────────────────────────────
    // Buat adapter + pasang click listener
    // ─────────────────────────────────────────────────────────────────────
    private fun buatAdapter(items: List<PortfolioItem>): PortfolioAdapter {
        return PortfolioAdapter(items) { item ->
            when (item) {

                // ── Ke detail Kegiatan / Pengalaman ──────────────────────
                is PortfolioItem.Pengalaman -> {
                    val d = item.data
                    startActivity(
                        Intent(this, ActivityDetailKegiatan::class.java).apply {
                            putExtra(ActivityDetailKegiatan.EXTRA_NAMA,          d.namaLengkap)
                            putExtra(ActivityDetailKegiatan.EXTRA_TANGGAL_LAHIR, d.tanggalLahir)
                            putExtra(ActivityDetailKegiatan.EXTRA_EMAIL,         d.email)
                            putExtra(ActivityDetailKegiatan.EXTRA_NO_HP,         d.noHp)
                            putExtra(ActivityDetailKegiatan.EXTRA_LOKASI,        d.lokasi)
                            putExtra(ActivityDetailKegiatan.EXTRA_POSISI,        d.jabatan)
                            putExtra(ActivityDetailKegiatan.EXTRA_PERUSAHAAN,    d.organisasi)
                            putExtra(ActivityDetailKegiatan.EXTRA_TAHUN,         d.tahun)
                            putExtra(ActivityDetailKegiatan.EXTRA_DESKRIPSI,     d.deskripsi)
                            putExtra(ActivityDetailKegiatan.EXTRA_KEAHLIAN,      d.keahlian)
                        }
                    )
                }

                // ── Ke detail Project ─────────────────────────────────────
                is PortfolioItem.ProjectEntry -> {
                    val d = item.data
                    startActivity(
                        Intent(this, ActivityDetailProject::class.java).apply {
                            putExtra(ActivityDetailProject.EXTRA_NAMA_PROJECT, d.namaProject)
                            putExtra(ActivityDetailProject.EXTRA_TEKNOLOGI,    d.teknologi)
                            putExtra(ActivityDetailProject.EXTRA_TAHUN,        d.tahun)
                            putExtra(ActivityDetailProject.EXTRA_LINK,         d.linkProject)
                            putExtra(ActivityDetailProject.EXTRA_DESKRIPSI,    d.deskripsi)
                        }
                    )
                }
            }
        }
    }

    // ─────────────────────────────────────────────────────────────────────
    // Update badge jumlah item
    // ─────────────────────────────────────────────────────────────────────
    private fun updateBadge(jumlah: Int) {
        tvJumlah.text = jumlah.toString()
    }
}
