package com.example.ff9

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.composable
import androidx.navigation.navArgument

object Routes {
    const val screenHome = "screen_home"
    const val screenChooseCharacter = "screen_choose_character"
    const val screenDetailsCharacter = "screen_details_character"

}

@Composable
fun AppNavigation() {
    // 2. Initialisation du NavController
    val navController = rememberNavController()

    // 3. Configuration du NavHost
    NavHost(
        navController = navController,
        startDestination = Routes.screenHome,

    ) {
        // Écran A
        composable(Routes.screenHome) {
            HomeScreen(
                onNavigateToListCharacters = {
                    navController.navigate(Routes.screenChooseCharacter)
                }
            )
        }

        composable(Routes.screenChooseCharacter) {

            val chooseCharacterViewModel: ChooseCharacterViewModel = viewModel()
            ListChooseCharacterScreen(
                chooseCharacterViewModel,
                onBack = { navController.popBackStack() },
                onNavigateToCharacterDetails = { idCharacter ->
                    navController.navigate("${Routes.screenDetailsCharacter}/$idCharacter")
                }
            )
        }

        composable(
            route = "${Routes.screenDetailsCharacter}/{idCharacter}",
            arguments = listOf(navArgument("idCharacter") { type = NavType.StringType })
        ) { backStackEntry ->

            val detailsCharacterViewModel: DetailsCharacterViewModel = viewModel()

            val characterIdParams = backStackEntry.arguments?.getString("idCharacter")?.toInt() ?: 0

            LaunchedEffect(characterIdParams) {
                detailsCharacterViewModel.getCharacterById(characterIdParams)
                detailsCharacterViewModel.getSkillsCombatByCharacterId(characterIdParams)
                detailsCharacterViewModel.getSkillsSupportByCharacterId(characterIdParams)
                detailsCharacterViewModel.getWeaponsDetailsByCharacterId(characterIdParams)

            }

            DetailsCharacterScreen(
                detailsCharacterViewModel,
                onBack = { navController.popBackStack(Routes.screenChooseCharacter,inclusive = false) }
            )
        }
    }
}


