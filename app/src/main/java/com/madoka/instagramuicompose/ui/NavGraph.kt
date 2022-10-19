package com.madoka.instagramuicompose.ui

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.madoka.instagramuicompose.ui.courses.CourseTabs
import com.madoka.instagramuicompose.ui.courses.Tabs
import com.madoka.instagramuicompose.ui.onboarding.Onboarding


/**
 * Destinations used in the ([OwlApp]).
 */
object MainDestinations {
    const val COURSES_ROUTE = "courses"
    const val COURSE_DETAIL_ROUTE = "course"
    const val ONBOARDING_ROUTE = "onboarding"
    const val COURSE_DETAIL_ID_KEY = "courseId"
}


@Composable
fun NavGraph(
    modifier: Modifier = Modifier,
    finishActivity: () -> Unit = {},
    navController: NavHostController = rememberNavController(),
    startDestination: String = MainDestinations.COURSES_ROUTE,
    showOnboardingInitially: Boolean = true
) {
    // Onboarding could be read from shared preferences.
    val onboardingComplete = remember(showOnboardingInitially) {
        mutableStateOf(!showOnboardingInitially)
    }

    val actions = remember(navController) { MainActions(navController) }

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        /* On-boarding section The on-boarding screen allows users to customize their experience by selecting topics.**/
        composable(MainDestinations.ONBOARDING_ROUTE) {
            /* Intercept back in On-boarding: make it finish the activity*/
            BackHandler {
                finishActivity()
            }

            Onboarding(
                onboardingComplete = {
                    /* Set the flag so that onboarding is not shown next time.*/
                    onboardingComplete.value = true
                    actions.onboardingComplete()
                }
            )
        }


        //Bottom Navigation
        navigation(
            route = MainDestinations.COURSES_ROUTE,
            startDestination = CourseTabs.FEATURED.route
        ) {
            //Bottom Nav
           Tabs(
               onCourseSelected = actions.openCourse,
               onboardingComplete = onboardingComplete,
                navController = navController,
                modifier = modifier
            )
        }

    }
}


/**
 * Models the navigation actions in the app.
 */
class MainActions(navController: NavHostController) {
    val onboardingComplete: () -> Unit = {
        navController.popBackStack()
    }


    // Used from COURSES_ROUTE
    val openCourse = { newCourseId: Long, from: NavBackStackEntry ->
        // In order to discard duplicated navigation events, we check the Lifecycle
        if (from.lifecycleIsResumed()) {
            navController.navigate("${MainDestinations.COURSE_DETAIL_ROUTE}/$newCourseId")
        }
    }
}

/**
 * If the lifecycle is not resumed it means this NavBackStackEntry already processed a nav event.
 *
 * This is used to de-duplicate navigation events.
 */
private fun NavBackStackEntry.lifecycleIsResumed() =
    this.lifecycle.currentState == Lifecycle.State.RESUMED



/**
object MainDestinations {
const val ONBOARDING_ROUTE = "onboarding"
const val COURSES_ROUTE = "courses"
const val COURSE_DETAIL_ROUTE = "course"
const val COURSE_DETAIL_ID_KEY = "courseId"
}


@Composable
fun NavGraph(
modifier: Modifier = Modifier,
finishActivity: () -> Unit = {},
navController: NavHostController = rememberNavController(),
startDestination: String = MainDestinations.COURSES_ROUTE,
showOnboardingInitially: Boolean = true
) {

val actions = remember(navController) { MainActions(navController) }
NavHost(
navController = navController,
startDestination = startDestination
) {
composable(MainDestinations.ONBOARDING_ROUTE) {
// Intercept back in Onboarding: make it finish the activity
BackHandler {
finishActivity()
}

//            Onboarding(
//                onboardingComplete = {
//                    // Set the flag so that onboarding is not shown next time.
//                    onboardingComplete.value = true
//                    actions.onboardingComplete()
//                }
//            )
}
navigation(
route = MainDestinations.COURSES_ROUTE ,
startDestination = CourseTabs.FEATURED.route
) {
courses(
//               // onCourseSelected= actions.openCourse,
//                navController = navController,
//                modifier = modifier
}

}
}

/**
 * Models the navigation actions in the app.
*/
class MainActions(navController: NavHostController) {

// Used from COURSES_ROUTE //TABS ROUTES
val openCourse = { newCourseId: Long, from: NavBackStackEntry ->
// In order to discard duplicated navigation events, we check the Lifecycle
if (from.lifecycleIsResumed()) {
navController.navigate("${MainDestinations.COURSE_DETAIL_ROUTE}/$newCourseId")
}
}
}

/**
 * If the lifecycle is not resumed it means this NavBackStackEntry already processed a nav event.
 *
 * This is used to de-duplicate navigation events.
*/
private fun NavBackStackEntry.lifecycleIsResumed() =
this.lifecycle.currentState == Lifecycle.State.RESUMED


 */