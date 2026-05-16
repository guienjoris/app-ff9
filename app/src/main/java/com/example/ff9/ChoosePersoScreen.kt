package com.example.ff9

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.ff9.data.entities.Perso

@Composable
fun ListChoosePersoScreen(viewModel: ChoosePersoViewModel, onBack: () -> Unit, onNavigateToPersoDetails:(idPerso:Int)-> Unit){
    val persos by viewModel.persoState.collectAsState()
    ListChoosePerso(persos,onNavigateToPersoDetails,onBack)
}

@Composable
fun ListChoosePerso(persos: List<Perso>, onNavigateToPersoDetails:(Int)-> Unit, onBack:() -> Unit){
    Column{
        ButtonBack(onBack)

        LazyVerticalGrid(columns= GridCells.Fixed(2),
            contentPadding = PaddingValues(2.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(8.dp)
        ) {
            items(persos){ perso ->
                CardChoosePerso(perso,onNavigateToPersoDetails)
            }
        }
    }
}

@Composable
fun CardChoosePerso(perso: Perso,onNavigateToPersoDetails:(Int)-> Unit){


    Card(modifier = Modifier,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    ) {
        Row(horizontalArrangement = Arrangement.spacedBy(5.dp),
            modifier = Modifier.fillMaxSize()
                .clickable(enabled=true,
                    onClick = {onNavigateToPersoDetails(perso.id)}
                ),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Image(painter = painterResource(getResIdByName(perso.profilePictureId) ?: R.drawable.logo_ico),
                contentDescription = null,

                )
            Text(text= perso.firstName,
                style= MaterialTheme.typography.titleLarge
            )
            Text(text=perso.lastName ?: "",
                style= MaterialTheme.typography.titleLarge
            )


        }
    }

}





