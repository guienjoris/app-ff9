package com.example.ff9

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
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

    const val screenListWeapon = "screen_list_weapon"

}

@Composable
fun AppNavigation() {
    // 2. Initialisation du NavController
    val navController = rememberNavController()

    // 3. Configuration du NavHost
    NavHost(
        navController = navController,
        startDestination = Routes.screenHome,
        // Animation quand un écran apparaît
        enterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(500)
            ) + fadeIn(animationSpec = tween(500))
        },
        // Animation quand un écran disparaît (vers l'avant)
        exitTransition = {
            slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(500)
            ) + fadeOut(animationSpec = tween(500))
        },
        // Animation quand on fait un retour arrière (l'écran précédent réapparaît)
        popEnterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(500)
            )
        },
        // Animation quand l'écran actuel est détruit (retour arrière)
        popExitTransition = {
            slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(500)
            )
        }

    ) {
        // Écran A
        composable(Routes.screenHome) {
            HomeScreen(
                onNavigateToListCharacters = {
                    navController.navigate(Routes.screenChooseCharacter)
                },
                onNavigateToListWeapon ={
                    navController.navigate(Routes.screenListWeapon)
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
                detailsCharacterViewModel.getWeaponsDetailsByCharacterId(characterIdParams)

            }

            DetailsCharacterScreen(
                detailsCharacterViewModel,
                onBack = { navController.popBackStack(Routes.screenChooseCharacter,inclusive = false) }
            )
        }

        composable(Routes.screenListWeapon){

            val allWeaponsViewModel: ListWeaponViewModel = viewModel()

            ListWeaponScreen(allWeaponsViewModel,
                onBack = { navController.popBackStack(Routes.screenHome,inclusive = false) }
                )
        }
    }
}


