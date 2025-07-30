# Icon Management Guide

## Menambahkan Icon Manual ke Android Project

### 1. Format yang Direkomendasikan: SVG → Vector Drawable

**Alasan menggunakan SVG:**

- ✅ Scalable - tidak pixelated di berbagai ukuran layar
- ✅ File size kecil
- ✅ Dapat di-tint (ubah warna) dengan mudah
- ✅ Native Android support melalui Vector Drawables
- ✅ Support berbagai density screen tanpa multiple file

### 2. Langkah-langkah Menambahkan Icon

#### Opsi A: Import SVG langsung (Recommended)

1. Klik kanan pada folder `res/drawable`
2. Pilih `New` → `Vector Asset`
3. Pilih `Local file (SVG, PSD)`
4. Browse dan pilih file SVG Anda
5. Android Studio akan otomatis convert ke Vector Drawable
6. Beri nama icon (contoh: `ic_history.xml`)

#### Opsi B: Manual Vector Drawable

1. Buat file XML di `res/drawable/ic_nama_icon.xml`
2. Gunakan format seperti contoh di bawah:

```xml
<vector xmlns:android="http://schemas.android.com/apk/res/android"
    android:width="24dp"
    android:height="24dp"
    android:viewportWidth="24"
    android:viewportHeight="24"
    android:tint="?attr/colorOnSurface">
  <path
      android:fillColor="@android:color/white"
      android:pathData="M13,3c-4.97,0 -9,4.03 -9,9L1,12l3.89,3.89..."/>
</vector>
```

### 3. Menggunakan Icon di Compose

```kotlin
// Untuk Vector Drawable (XML)
Icon(
    painter = painterResource(id = R.drawable.ic_history),
    contentDescription = "History",
    tint = Color.Gray
)

// Untuk Material Icons (Built-in)
Icon(
    imageVector = Icons.Default.History,
    contentDescription = "History",
    tint = Color.Gray
)
```

### 4. Icon yang Sudah Dibuat

Dalam project ini telah dibuat:

- `ic_history.xml` - Icon untuk search history
- `ic_close.xml` - Icon untuk close/delete
- `ic_recent.xml` - Icon alternatif untuk recent items

### 5. Sumber Icon SVG Gratis

**Material Design Icons:**

- https://fonts.google.com/icons
- https://materialdesignicons.com/

**Other Sources:**

- https://heroicons.com/
- https://feathericons.com/
- https://iconify.design/

### 6. Best Practices

1. **Ukuran**: Gunakan 24x24dp untuk icon standar
2. **Naming**: Gunakan prefix `ic_` untuk icon (ic_history, ic_close)
3. **Tintable**: Gunakan `android:tint="?attr/colorOnSurface"` untuk adaptif tema
4. **Konsistensi**: Gunakan icon dari satu keluarga (Material, Heroicons, dll)
5. **Accessibility**: Selalu berikan `contentDescription`

### 7. Troubleshooting

**Problem**: Icon tidak ditemukan (Can't resolve symbol)
**Solution**:

1. Clean and rebuild project
2. Pastikan file XML ada di `res/drawable/`
3. Sync project dengan Gradle

**Problem**: Icon tidak muncul
**Solution**:

1. Periksa path di Vector Drawable
2. Pastikan `android:fillColor` diset
3. Cek viewportWidth dan viewportHeight
