package com.example.ff9.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun TextTitle(text:String){
    Text(text=text,
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp,
        maxLines = 1,
        modifier= Modifier
    )
}