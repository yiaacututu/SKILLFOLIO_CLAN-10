Nama: Cutrin Joy M.T Sihombing 
NPM: 24082010170
Mata Kuliah: Pemrograman Mobile
Clan: 10(Barbie)
Bagian Pengerjaan: Interface untuk menampilkan informasi detail dari recycleview

Detail Aplikasi
Skillfolio adalah aplikasi Android Native berbasis Kotlin yang dirancang untuk membantu pengguna mendokumentasikan dan menampilkan portofolio pribadi secara digital. Dengan Skillfolio, pengguna dapat mencatat riwayat pengalaman organisasi/kerja serta project yang pernah dikerjakan, semuanya dalam satu aplikasi yang rapi dan mudah digunakan. 
Aplikasi ini merupakan tugas mata kuliah Pemrograman Mobile (Semester 4) yang dikembangkan secara berkelompok dengan pendekatan Android Native menggunakan komponen standar seperti RecyclerView, ViewBinding, dan in-memory Repository pattern.

Fitur Utama Aplikasi:
- Kelola Pengalaman: Tambah dan lihat riwayat pengalaman organisasi/kerja
- Kelola Project: Tambah dan lihat daftar project beserta teknologi yang digunakan
- Pencarian Real-Time: Filter pengalaman & project secara langsung lewat search bar
- Halaman Detail: Lihat informasi lengkap dari setiap pengalaman atau project
- Badge Jumlah Item: Tampilan jumlah total entri secara dinamis
- Ekspor CV: (Coming soon) Fitur ekspor portofolio menjadi CV

Alur Navigasi Aplikasi:
- Halaman Utama (ActivityListKegiatan): Menjadi pusat tampilan untuk melihat semua daftar pengalaman dan proyek yang telah tersimpan.
- Tambah Data: Klik tombol "+" (FAB) untuk masuk ke formulir input pengalaman di MainActivity.
- Pilih Kategori: Di dalam form, gunakan Tab Project jika ingin beralih ke formulir TambahProjectActivity.
- Lihat Detail Pengalaman: Klik salah satu item pengalaman di daftar utama untuk membuka ActivityDetailKegiatan.
- Lihat Detail Proyek: Klik salah satu item proyek di daftar utama untuk melihat rinciannya di ActivityDetailProject.

Anggota Kelompok dan Pembagian:
- Kaka Dimas Soehendra (24082010171) = Interface Pendataan (UI Layout & Design)
  Bertanggung jawab atas desain dan implementasi antarmuka (UI) seluruh halaman aplikasi, termasuk layout form pendataan pengalaman dan project, tampilan halaman utama daftar portofolio, serta keseluruhan visual aplikasi menggunakan XML layout Android.
- Gratia Novelin Tamba (24082010178) = Pengolahan Data dari Form Input
  Pengolahan Data dari Form
Bertanggung jawab atas logika pengambilan, validasi, dan pemrosesan data yang diinput oleh pengguna melalui form, termasuk handling input pengalaman (jabatan, organisasi, tahun, deskripsi) dan input project, serta penyimpanan data ke repository.
- Lutfia Nur Sabrina (24082010175) = Implementasi RecyclerView & Adapter
  Bertanggung jawab atas implementasi komponen RecyclerView sebagai daftar portofolio, termasuk pembuatan PortfolioAdapter yang menangani dua jenis item (Pengalaman & Project) dalam satu daftar campuran, serta fitur pencarian/filter real-time.
- Cutrin Joy M.T Sihombing = Data & Repository RecyclerView
  Bertanggung jawab atas pengelolaan data yang ditampilkan di RecyclerView, termasuk struktur data (PortfolioItem, sealed class), PortfolioRepository sebagai sumber data terpusat, serta mekanisme refresh data otomatis saat kembali ke halaman utama.
