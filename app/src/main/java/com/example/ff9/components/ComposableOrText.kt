package com.example.ff9.components

import androidx.compose.runtime.Composable

sealed class ComposableOrText {
    data class Text(val value: String) : ComposableOrText()
    data class Custom(val content: @Composable () -> Unit) : ComposableOrText()
}