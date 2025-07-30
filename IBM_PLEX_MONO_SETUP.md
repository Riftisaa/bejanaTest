# IBM Plex Mono Font Setup Instructions

## Langkah-langkah untuk mengubah font ke IBM Plex Mono:

### 1. Download Font Files

- Kunjungi https://fonts.google.com/specimen/IBM+Plex+Mono
- Download IBM Plex Mono font family
- Atau download dari IBM GitHub: https://github.com/IBM/plex/releases

### 2. Font Files yang Dibutuhkan

Ganti file placeholder berikut dengan font asli:

- `app/src/main/res/font/ibm_plex_mono_regular.ttf`
- `app/src/main/res/font/ibm_plex_mono_medium.ttf`
- `app/src/main/res/font/ibm_plex_mono_bold.ttf`

### 3. Perubahan yang Sudah Dilakukan

✅ Dibuat folder `app/src/main/res/font/`
✅ Dibuat `ibm_plex_mono.xml` font family definition
✅ Updated `Type.kt` dengan IBM Plex Mono typography
✅ Updated `SearchBar.kt` untuk menggunakan font yang benar
✅ Semua text styles menggunakan IBM Plex Mono dengan fallback ke FontFamily.Monospace

### 4. Komponen yang Terpengaruh

- **HomeScreen**: Text error akan menggunakan IBM Plex Mono
- **SearchBar**: Input dan placeholder menggunakan IBM Plex Mono
- **CharacterItem**: Nama karakter, species/status, dan lokasi menggunakan IBM Plex Mono
- **Semua text di aplikasi** akan menggunakan IBM Plex Mono melalui MaterialTheme.typography

### 5. Alternative: Menggunakan Google Fonts API

Jika ingin menggunakan Google Fonts tanpa download manual:

1. Tambahkan dependency di `app/build.gradle.kts`:

```kotlin
implementation("androidx.compose.ui:ui-text-google-fonts:1.5.4")
```

2. Update `Type.kt`:

```kotlin
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.text.googlefonts.Font

val provider = GoogleFont.Provider(
    providerAuthority = "com.google.android.gms.fonts",
    providerPackage = "com.google.android.gms",
    certificates = R.array.com_google_android_gms_fonts_certs
)

val IBMPlexMono = FontFamily(
    Font(GoogleFont("IBM Plex Mono"), provider)
)
```

### 6. Menjalankan Aplikasi

Setelah mengganti font files dengan yang asli:

1. Clean project: `./gradlew clean`
2. Build project: `./gradlew build`
3. Run aplikasi

Semua text dalam HomeScreen akan menggunakan IBM Plex Mono font!
