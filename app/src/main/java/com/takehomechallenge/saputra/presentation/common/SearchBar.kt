package com.takehomechallenge.saputra.presentation.common

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.takehomechallenge.saputra.ui.theme.BejanaTestTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    onFocusChanged: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "Search characters...",
    backgroundColor: Color = Color(0xFF87F54E), // Warna hijau muda
    onSearchSubmit: ((String) -> Unit)? = null
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    
    OutlinedTextField(
        value = query,
        onValueChange = onQueryChange,
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
            .onFocusChanged { focusState ->
                onFocusChanged(focusState.isFocused)
            },
        placeholder = {
            Text(
                text = placeholder,
                style = MaterialTheme.typography.bodyMedium
            )
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search"
            )
        },
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Search
        ),
        keyboardActions = KeyboardActions(
            onSearch = {
                onSearchSubmit?.invoke(query)
                keyboardController?.hide()
            }
        ),
        shape = RoundedCornerShape(12.dp),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            containerColor = backgroundColor, // Menggunakan parameter backgroundColor
            focusedBorderColor = Color.Black,
            unfocusedBorderColor = Color.LightGray,
            focusedTextColor = Color.Black,
            unfocusedTextColor = Color.Black,
            focusedLeadingIconColor = Color.Gray,
            unfocusedLeadingIconColor = Color.Black,
            cursorColor = Color.Black
        ),
        singleLine = true,
        textStyle = MaterialTheme.typography.bodyMedium
    )
}

@Preview(showBackground = true)
@Composable
fun SearchBarPreview() {
    BejanaTestTheme {
        SearchBar(
            query = "",
            onQueryChange = {},
            onFocusChanged = {},
            onSearchSubmit = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SearchBarWithQueryPreview() {
    BejanaTestTheme {
        SearchBar(
            query = "Rick",
            onQueryChange = {},
            onFocusChanged = {},
            onSearchSubmit = {}
        )
    }
} 