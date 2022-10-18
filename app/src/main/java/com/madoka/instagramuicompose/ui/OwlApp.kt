package com.madoka.instagramuicompose.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.madoka.instagramuicompose.ui.courses.CourseTabs
import com.madoka.instagramuicompose.ui.theme.BlueTheme
import java.util.*


@Composable
fun OwlApp(finishActivity: () -> Unit) {
    BlueTheme {
        val tabs = remember { CourseTabs.values() }
    val navController = rememberNavController()

        Scaffold(
            backgroundColor = MaterialTheme.colors.primarySurface,
            bottomBar = { OwlBottomBar( navController = navController,  tabs) }
        ) { innerPaddingModifier ->
            NavGraph(
                finishActivity = finishActivity,
                navController = navController,
                modifier = Modifier.padding(innerPaddingModifier)
            )
        }
    }
}

@Composable
fun OwlBottomBar( navController: NavController, tabs: Array<CourseTabs>) {

    val navBackStackEntry by navController.currentBackStackEntryAsState()

    val currentRoute = navBackStackEntry?.destination?.route
        //?: CourseTabs.FEATURED.route

    val routes = remember { CourseTabs.values().map { it.route } }

   if (currentRoute in routes) {
        BottomNavigation(
            Modifier.
            windowInsetsBottomHeight(
                WindowInsets.navigationBars.add(WindowInsets(bottom = 56.dp))
            )
        ) {
            tabs.forEach { tab ->
                BottomNavigationItem(
                    icon = { Icon(painterResource(tab.icon), contentDescription = null) },
                    label = { Text(stringResource(tab.title).uppercase(Locale.getDefault())) },
                    selected = currentRoute == tab.route,
                    onClick = {
                        if (tab.route != currentRoute) {
                            navController.navigate(tab.route) {
                                popUpTo(navController.graph.startDestinationId) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    },
                    alwaysShowLabel = false,
                    selectedContentColor = MaterialTheme.colors.secondary,
                    unselectedContentColor = LocalContentColor.current,
                    modifier = Modifier.navigationBarsPadding()
                )
            }
        }
    }
}