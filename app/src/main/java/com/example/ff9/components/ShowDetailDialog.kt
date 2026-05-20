package com.example.ff9.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun ShowDetailDialog(
    item: ComposableOrText,
    description: ComposableOrText,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            when(item) {
                is ComposableOrText.Text -> Text(text=item.value)
                is ComposableOrText.Custom -> item.content()
            }
        },
        text = {
            when(description) {
                is ComposableOrText.Text -> Text(text=description.value)
                is ComposableOrText.Custom -> description.content()
            }
        },
        confirmButton = {
            Button(onClick = onDismiss) {
                Text("Ok")
            }
        },
    )
}