package com.madoka.instagramuicompose.ui.courses

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.madoka.instagramuicompose.R
import com.madoka.instagramuicompose.ui.MainDestinations

fun NavGraphBuilder.Tabs(
    //onCourseSelected: (Long, NavBackStackEntry) -> Unit,
    onboardingComplete: State<Boolean>,
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    composable(CourseTabs.FEATURED.route) { from ->
        /** Show on-boarding instead if not shown yet.*/
        LaunchedEffect(onboardingComplete) {
            if (!onboardingComplete.value) {
                navController.navigate(MainDestinations.ONBOARDING_ROUTE)
            }
        }
        if (onboardingComplete.value) {  /* Avoid glitch when showing on-boarding */
            FeaturedCourses()
        }
    }

    composable(CourseTabs.MY_COURSES.route) {
        MyCourses()
    }

    composable(CourseTabs.SEARCH.route) {
        SearchCourses()
    }
}

/**
@Composable
fun CoursesAppBar() {
TopAppBar(
elevation = 0.dp,
modifier = Modifier.height(80.dp)
) {
Image(
modifier = Modifier
.padding(16.dp)
.align(Alignment.CenterVertically),
painter = painterResource(id = R.drawable.ic_lockup_white),
contentDescription = null
)
IconButton(
modifier = Modifier.align(Alignment.CenterVertically),
onClick = { /* todo */ }
) {
Icon(
imageVector = Icons.Filled.AccountCircle,
contentDescription = stringResource(R.string.label_profile)
)
}
}
}  **/

enum class CourseTabs(
    @StringRes val title: Int,
    @DrawableRes val icon: Int,
    val route: String
) {
    MY_COURSES(R.string.my_courses, R.drawable.ic_grain, CoursesDestinations.MY_COURSES_ROUTE),
    FEATURED(R.string.featured, R.drawable.ic_featured, CoursesDestinations.FEATURED_ROUTE),
    SEARCH(R.string.search, R.drawable.ic_search, CoursesDestinations.SEARCH_COURSES_ROUTE)
}

/**
 * Destinations used in the ([OwlApp]).
 */
private object CoursesDestinations {
    const val FEATURED_ROUTE = "courses/featured"
    const val MY_COURSES_ROUTE = "courses/my"
    const val SEARCH_COURSES_ROUTE = "courses/search"
}

