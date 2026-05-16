package com.example.ff9

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.ff9.ui.theme.FF9Theme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            FF9Theme {
                Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
                    com.example.ff9.FFTopAppBar(modifier = Modifier.padding(bottom = 10.dp))
                }) { innerPadding ->
                    Column(modifier= Modifier.padding(innerPadding)){
                        AppNavigation()
                    }

                }
            }
        }
    }
}



@Composable
fun HomeScreen(onNavigateToListCharacters:  () -> Unit){
    Column(horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier= Modifier.fillMaxWidth()){
        Button(onClick = onNavigateToListCharacters ) {
            Text(text="Personnages")
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FFTopAppBar(modifier: Modifier = Modifier) {
    CenterAlignedTopAppBar(
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
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
@SuppressLint("DiscouragedApi")
@Composable
fun getResIdByName(resName: String?): Int {
    if (resName == null) return R.drawable.logo_ico // Image par défaut si vide

    val context = LocalContext.current
    // On nettoie le nom au cas où tu as écrit "R.drawable.nom" au lieu de juste "nom"
    val cleanName = resName.replace("R.drawable.", "")

    // C'est l'équivalent de faire R.drawable.$nom
    val resId = context.resources.getIdentifier(cleanName, "drawable", context.packageName)

    return if (resId != 0) resId else R.drawable.logo_ico // On gère si l'image n'existe pas
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


