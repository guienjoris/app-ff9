package com.example.ff9.components

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import com.example.ff9.Category

@Composable
fun ExpendableCard(iconRes: Painter, text: Category, content: @Composable ()-> Unit){
    var expanded by remember { mutableStateOf(false) }


    Card(onClick = { expanded = !expanded },
        modifier = Modifier.animateContentSize(
            animationSpec = tween(
                durationMillis = 300, // Durée de l'animation
                easing = LinearOutSlowInEasing // Courbe de vitesse fluide
            )
        )) {
        Column(modifier= Modifier
            .padding(8.dp)
        ) {
            Row(horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier=Modifier.fillMaxWidth()
            ) {
                Image(painter=iconRes,
                    contentDescription = null,
                    modifier=Modifier
                        .size(30.dp)
                        .weight(1f))
                Text(text = text.value,
                    modifier=Modifier.weight(3f)
                )
                ExpandCardButton(
                    expanded,
                    onClick = { expanded = !expanded }
                )
            }
            if(expanded){
                Column(modifier=Modifier.verticalScroll(rememberScrollState())){
                    content()
                }

            }
        }

    }
}