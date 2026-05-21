package com.example.ff9.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
    title: String,
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
        TextTitle(text = title)

        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            contentPadding = PaddingValues(5.dp),
            modifier = modifier
        ) {
            items(items) { itemPair -> // Plus besoin d'index !
                Column {
                    Button(
                        onClick = {
                            selectedItem = itemPair // On sauvegarde le couple entier
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
                            // Affichage du bouton
                            when (val display = itemPair.display) {
                                is ComposableOrText.Text -> Text(text = display.value)
                                is ComposableOrText.Custom -> display.content()
                            }
                        }
                    }
                }
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
// Une petite classe helper pour lier le bouton à sa description
data class GridItemPair(
    val display: ComposableOrText,
    val description: ComposableOrText
)
/*@Composable
fun GridSection(title:String,
                        lists: List<ComposableOrText>,
                        listDescription: List<ComposableOrText>,
                        globalPainter: Painter?,
                        modifier:Modifier
){
    var showDialog by remember { mutableStateOf(false) }
    var selectedItem by remember { mutableStateOf<ComposableOrText?>(null) }
    var selectedItemIndex by remember { mutableIntStateOf(0) }


    Column(verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
        ){
        TextTitle(text=title)
        LazyVerticalGrid(columns= GridCells.Fixed(3),
            contentPadding = PaddingValues(5.dp),
            modifier = modifier
        ) {
            itemsIndexed(lists) { index, item ->
                Column{
                    Button(onClick = { selectedItem = item
                        selectedItemIndex= index
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
                        Column(horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,){
                            if(globalPainter != null){
                                Image(painter = globalPainter,
                                    contentDescription = null,
                                    modifier = Modifier.size(50.dp)
                                )
                            }
                            when(item) {
                                is ComposableOrText.Text -> Text(text=item.value)
                                is ComposableOrText.Custom -> item.content()
                            }
                        }
                    }
                }
            }
        }


        println(listDescription[selectedItemIndex])
        if(showDialog && selectedItem != null){
            ShowDetailDialog(item = selectedItem!!,
                description = listDescription[selectedItemIndex],
                onDismiss = { showDialog = false },
            )
        }
    }
}*/

