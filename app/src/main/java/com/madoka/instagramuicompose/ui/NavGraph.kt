package com.madoka.instagramuicompose.ui

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.madoka.instagramuicompose.ui.MainDestinations.COURSE_DETAIL_ID_KEY
import com.madoka.instagramuicompose.ui.course.CourseDetails
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
           Tabs(
              onCourseSelected = actions.openCourse,//when a course in the @composable Activity is selected ..not a tab
               onboardingComplete = onboardingComplete,
                navController = navController,
                modifier = modifier
            )
        }

        composable(
            "${MainDestinations.COURSE_DETAIL_ROUTE}/{$COURSE_DETAIL_ID_KEY}",
            arguments = listOf(
                navArgument(COURSE_DETAIL_ID_KEY) { type = NavType.LongType }
            )
        ) { backStackEntry: NavBackStackEntry ->
            val arguments = requireNotNull(backStackEntry.arguments)
            val currentCourseId = arguments.getLong(COURSE_DETAIL_ID_KEY)
            CourseDetails(
                courseId = currentCourseId,
                selectCourse = { newCourseId ->
                    actions.relatedCourse(newCourseId, backStackEntry)
                },
                upPress = { actions.upPress(backStackEntry) }
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
    // Used from COURSE_DETAIL_ROUTE
    val relatedCourse = { newCourseId: Long, from: NavBackStackEntry ->
        // In order to discard duplicated navigation events, we check the Lifecycle
        if (from.lifecycleIsResumed()) {
            navController.navigate("${MainDestinations.COURSE_DETAIL_ROUTE}/$newCourseId")
        }
    }

    // Used from COURSE_DETAIL_ROUTE
    val upPress: (from: NavBackStackEntry) -> Unit = { from ->
        // In order to discard duplicated navigation events, we check the Lifecycle
        if (from.lifecycleIsResumed()) {
            navController.navigateUp()
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


