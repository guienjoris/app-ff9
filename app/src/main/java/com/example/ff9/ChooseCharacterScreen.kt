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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.ff9.data.entities.Character
import com.example.ff9.components.ButtonBack

@Composable
fun ListChooseCharacterScreen(viewModel: ChooseCharacterViewModel, onBack: () -> Unit, onNavigateToCharacterDetails:(idCharacter:Int)-> Unit){
    val characters by viewModel.characterState.collectAsState()
    ListChooseCharacter(characters,onNavigateToCharacterDetails,onBack)
}

@Composable
fun ListChooseCharacter(characters: List<Character>, onNavigateToCharacterDetails:(Int)-> Unit, onBack:() -> Unit){
    Column{
        ButtonBack(onBack)

        LazyVerticalGrid(columns= GridCells.Fixed(2),
            contentPadding = PaddingValues(2.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(8.dp)
        ) {
            items(characters){ character ->
                CardChooseCharacter(character,onNavigateToCharacterDetails)
            }
        }
    }
}

@Composable
fun CardChooseCharacter(character: Character, onNavigateToCharacterDetails:(Int)-> Unit){


    Card(modifier = Modifier.padding(10.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer
        )
    ) {
        Row(horizontalArrangement = Arrangement.spacedBy(5.dp),
            modifier = Modifier.fillMaxSize()
                .clickable(enabled=true,
                    onClick = {onNavigateToCharacterDetails(character.idCharacter)}
                ),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Image(painter = painterResource(getResIdByName(character.profilePictureId)),
                contentDescription = null,

                )
            Column{
                Text(text= character.firstName,
                    style= MaterialTheme.typography.titleLarge,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(text=character.lastName ?: "",
                    style= MaterialTheme.typography.titleLarge,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }



        }
    }

}





