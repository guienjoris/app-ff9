package com.example.ff9.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp


@Composable
fun GridSection(
    title: ComposableOrText,
    items: List<GridItemPair>, // Reçoit la liste unifiée
    modifier: Modifier
) {
    var showDialog by remember { mutableStateOf(false) }
    // On stocke directement tout l'objet sélectionné (visuel + description)
    var selectedItem by remember { mutableStateOf<GridItemPair?>(null) }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(modifier=Modifier.padding(10.dp)){
            when(title){
                is ComposableOrText.Text -> TextTitle(text = title.value)
                is ComposableOrText.Custom -> title.content()
            }
        }


        FlowRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp),
            maxItemsInEachRow = 3,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items.forEach { itemPair ->
                // On donne un poids ou une largeur pour s'assurer que chaque élément
                // occupe un tiers de l'espace disponible (comme GridCells.Fixed(3))
                Box(
                    modifier = Modifier.weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Button(
                        onClick = {
                            selectedItem = itemPair
                            showDialog = true
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent,
                            contentColor = LocalContentColor.current
                        ),
                        elevation = null,
                        shape = RectangleShape,
                        contentPadding = PaddingValues(0.dp)
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,
                        ) {
                            when (val display = itemPair.display) {
                                is ComposableOrText.Text -> Text(text = display.value)
                                is ComposableOrText.Custom -> display.content()
                            }
                        }
                    }
                }
            }
            val itemsOnLastRow = items.size % 3
            if (itemsOnLastRow > 0) {
                repeat(3 - itemsOnLastRow) {
                    Box(modifier = Modifier.weight(1f))
                }
            }



        // Plus de risque d'index hors limites [0], on lit directement la description associée !
        if (showDialog && selectedItem != null) {
            ShowDetailDialog(
                item = selectedItem!!.display,
                description = selectedItem!!.description,
                onDismiss = { showDialog = false },
            )
        }
    }
}
}
// Une petite classe helper pour lier le bouton à sa description
data class GridItemPair(
    val display: ComposableOrText,
    val description: ComposableOrText
)


