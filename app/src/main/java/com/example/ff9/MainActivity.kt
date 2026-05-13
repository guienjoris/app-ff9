package com.example.ff9

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.ff9.ui.theme.FF9Theme
import com.example.ff9.data.Perso
import com.example.ff9.data.persos

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FF9Theme {
                FF9App()
            }
        }
    }
}

@Composable
fun FF9App(){
    var selectedPerso: Perso? by remember { mutableStateOf(null)}

    val onSelectedPerso = { it:Perso -> selectedPerso = it}

    Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
        FFTopAppBar(modifier=Modifier.padding(bottom=10.dp).clickable(onClick = { selectedPerso = null}))
    }) { innerPadding ->
        if (selectedPerso !== null){
            Column(modifier=Modifier.padding(innerPadding)){
                CardPersoDetails(selectedPerso!!)
            }
        }else{
            ListChoosePerso(innerPadding,onSelectedPerso)
        }


    }
}

@Composable
fun CardChoosePerso(perso: Perso,onSelectedPerso:(Perso)-> Unit){


        Card(modifier = Modifier,
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer
            )
            ) {
            Row(horizontalArrangement = Arrangement.spacedBy(5.dp),
                modifier = Modifier.fillMaxSize()
                    .clickable(enabled=true,
                        onClick = {onSelectedPerso(perso)}
                    ),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Image(painter = painterResource(perso.profilePictureId),
                    contentDescription = null,

                    )
                Text(text= stringResource(perso.firstnameResourceId),
                    style= MaterialTheme.typography.titleLarge
                )
                Text(text= stringResource(perso.nameResourceId),
                    style= MaterialTheme.typography.titleLarge
                )


        }
    }

}



@Composable
fun ListChoosePerso(innerPadding: PaddingValues,onSelectedPerso:(Perso)-> Unit){

    LazyVerticalGrid(columns= GridCells.Fixed(2),
    contentPadding = innerPadding,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.padding(8.dp)
        ) {
            items(persos){ perso ->
                CardChoosePerso(perso,onSelectedPerso)
            }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FFTopAppBar(modifier: Modifier = Modifier) {
    CenterAlignedTopAppBar(
        title = {
            Row(verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,

                ) {
                Image(
                    modifier = Modifier.fillMaxWidth(),
                    painter = painterResource(R.drawable.ff9_logo),
                    contentScale = ContentScale.Fit,
                    contentDescription = null
                )
            }
        },

        modifier = modifier.height(90.dp)
    )
}

@Preview(showBackground = true)
@Composable
fun FF9Preview() {
    FF9Theme {
        FF9App()
    }
}