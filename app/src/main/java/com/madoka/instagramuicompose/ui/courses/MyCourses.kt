package com.madoka.instagramuicompose.ui.courses

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.madoka.instagramuicompose.ui.theme.BlueTheme


@Composable
fun MyCourses(
    courses: List<Course>,
    selectCourse: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier) {
        item {
            Spacer(Modifier.windowInsetsTopHeight(WindowInsets.statusBars))
        }
        item {
            CoursesAppBar()
        }
        itemsIndexed(
            items = courses,
            key = { _, course -> course.id }
        ) { index, course ->
            MyCourse(
                course = course,
                index = index,
                selectCourse = selectCourse
            )
        }
    }
}

@Composable
fun MyCourse(
    course: Course,
    index: Int,
    selectCourse: (Long) -> Unit
) {
    Row(modifier = Modifier.padding(bottom = 8.dp)) {
        val stagger = if (index % 2 == 0) 72.dp else 16.dp
        Spacer(modifier = Modifier.width(stagger))
        CourseListItem(
            course = course,
            onClick = { selectCourse(course.id) },
            shape = RoundedCornerShape(topStart = 24.dp),
            modifier = Modifier.height(96.dp)
        )
    }
}

@Preview(name = "My Courses")
@Composable
private fun MyCoursesPreview() {
    BlueTheme {
        MyCourses(
            courses = courses,
            selectCourse = { }
        )
    }
}





//
//@Composable
//fun   MyCourses(message: String, from: String) {
//    Column {
//        Text(
//            text = message,
//            fontSize = 36.sp,
//        )
//        Text(
//            text = from,
//            fontSize = 24.sp,
//        )
//    }
//}
//
//@Preview(showBackground = false)
//@Composable
//fun  MyCourses() {
//    MyCourses ("my courses!", "- from Emma")
//}
