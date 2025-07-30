# SearchBar Background Color Options

Berikut adalah cara mengubah warna background SearchBar dengan berbagai opsi warna:

## 1. Warna Solid (yang sudah diterapkan)

```kotlin
colors = TextFieldDefaults.outlinedTextFieldColors(
    containerColor = Color.White, // Background putih
    focusedBorderColor = Color.Gray,
    unfocusedBorderColor = Color.LightGray,
    focusedTextColor = Color.Black,
    unfocusedTextColor = Color.Black,
    placeholderColor = Color.Gray,
    focusedLeadingIconColor = Color.Gray,
    unfocusedLeadingIconColor = Color.Gray,
    cursorColor = Color.Gray
)
```

## 2. Opsi Warna Background Lainnya

### Background Transparan

```kotlin
containerColor = Color.Transparent
```

### Background Abu-abu Terang

```kotlin
containerColor = Color(0xFFF5F5F5)
```

### Background Biru Muda

```kotlin
containerColor = Color(0xFFE3F2FD)
```

### Background Hijau Muda

```kotlin
containerColor = Color(0xFFE8F5E8)
```

### Background Kuning Muda

```kotlin
containerColor = Color(0xFFFFF9C4)
```

### Background Custom dengan Alpha (Semi-transparan)

```kotlin
containerColor = Color.White.copy(alpha = 0.8f)
```

### Background Gradient (Advanced)

Untuk gradient background, Anda perlu menambahkan modifier background:

```kotlin
modifier = modifier
    .fillMaxWidth()
    .padding(16.dp)
    .background(
        brush = Brush.horizontalGradient(
            colors = listOf(Color(0xFFE3F2FD), Color(0xFFBBDEFB))
        ),
        shape = RoundedCornerShape(12.dp)
    )
    .onFocusChanged { focusState ->
        onFocusChanged(focusState.isFocused)
    }
```

## 3. Cara Menggunakan Material Theme Colors

Jika ingin menggunakan warna dari theme:

```kotlin
containerColor = MaterialTheme.colorScheme.surface
```

Atau warna primary:

```kotlin
containerColor = MaterialTheme.colorScheme.primaryContainer
```

## 4. Import Tambahan untuk Gradient

Jika menggunakan gradient, tambahkan import ini:

```kotlin
import androidx.compose.foundation.background
import androidx.compose.ui.graphics.Brush
```

## 5. Update Langsung di SearchBar.kt

Ganti nilai `containerColor = Color.White` dengan warna pilihan Anda dari opsi di atas.
