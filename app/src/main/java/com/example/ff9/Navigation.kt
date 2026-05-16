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
    const val screenChoosePerso = "screen_choose_perso"
    const val screenDetailsPerso = "screen_details_perso"
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
                onNavigateToListPersos = {
                    navController.navigate(Routes.screenChoosePerso)
                }
            )
        }

        composable(Routes.screenChoosePerso) {

            val choosePersoViewModel: ChoosePersoViewModel = viewModel()
            ListChoosePersoScreen(
                choosePersoViewModel,
                onBack = { navController.popBackStack() },
                onNavigateToPersoDetails = { idPerso ->
                    navController.navigate("${Routes.screenDetailsPerso}/$idPerso")
                }
            )
        }

        composable(
            route = "${Routes.screenDetailsPerso}/{idPerso}",
            arguments = listOf(navArgument("idPerso") { type = NavType.StringType })
        ) { backStackEntry ->

            val detailsPersoViewModel: DetailsPersoViewModel = viewModel()

            val persoIdParams = backStackEntry.arguments?.getString("idPerso")?.toInt() ?: 0

            LaunchedEffect(persoIdParams) {
                detailsPersoViewModel.getPersoById(persoIdParams)
            }

            DetailsPersoScreen(
                detailsPersoViewModel,
                onBack = { navController.popBackStack(Routes.screenChoosePerso,inclusive = false) }
            )
        }
    }
}


