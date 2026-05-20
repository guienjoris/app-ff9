package com.example.ff9.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun ExpandCardButton(
    expanded: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    IconButton(
        onClick = onClick,
        modifier = modifier
    ) {
        Icon(
            imageVector = if (expanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
            contentDescription = "icône pour afficher ou pas le contenu",
            tint = MaterialTheme.colorScheme.secondary
        )
    }
}

@Composable
fun ButtonBack(onBack:() -> Unit){
    IconButton(onClick = onBack) {
        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            tint=MaterialTheme.colorScheme.tertiary,
            contentDescription = "Back to the previous page"
        )
    }
}