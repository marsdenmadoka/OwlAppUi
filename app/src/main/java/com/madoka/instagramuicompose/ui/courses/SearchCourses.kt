package com.madoka.instagramuicompose.ui.courses

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.madoka.instagramuicompose.R
import com.madoka.instagramuicompose.ui.model.Topic
import com.madoka.instagramuicompose.ui.model.topics
import com.madoka.instagramuicompose.ui.theme.BlueTheme


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SearchCourses(
    topics: List<Topic>,
    modifier: Modifier = Modifier
) {
    val (searchTerm, updateSearchTerm) = remember { mutableStateOf(TextFieldValue("")) }
    LazyColumn(
        modifier = modifier
            .statusBarsPadding()
            .fillMaxHeight()
    ) {
        item { AppBar(searchTerm, updateSearchTerm) }
        val filteredTopics = getTopics(searchTerm.text, topics)
        items(
            items = filteredTopics,
            key = { it.name }
        ) { topic ->
            Text(
                text = topic.name,
                style = MaterialTheme.typography.h5,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(onClick = { /* todo */ })
                    .padding(
                        start = 16.dp,
                        top = 8.dp,
                        end = 16.dp,
                        bottom = 8.dp
                    )
                    .wrapContentWidth(Alignment.Start)
                    .animateItemPlacement()
            )
        }
    }
}

/**
 * This logic should live outside UI, but full arch omitted for simplicity in this sample.
 */
private fun getTopics(
    searchTerm: String,
    topics: List<Topic>
): List<Topic> {
    return if (searchTerm != "") {
        topics.filter { it.name.contains(searchTerm, ignoreCase = true) }
    } else {
        topics
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun AppBar(
    searchTerm: TextFieldValue,
    updateSearchTerm: (TextFieldValue) -> Unit
) {
    TopAppBar(elevation = 0.dp) {
        Image(
            painter = painterResource(id = R.drawable.ic_search),
            contentDescription = null,
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.CenterVertically)
        )
        // TODO hint
        BasicTextField(
            value = searchTerm,
            onValueChange = updateSearchTerm,
            textStyle = MaterialTheme.typography.subtitle1.copy(
                color = LocalContentColor.current
            ),
            maxLines = 1,
            cursorBrush = SolidColor(LocalContentColor.current),
            modifier = Modifier
                .weight(1f)
                .align(Alignment.CenterVertically)
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
}

@Preview(name = "Search Courses")
@Composable
private fun FeaturedCoursesPreview() {
    BlueTheme {
        SearchCourses(topics, Modifier)
    }
}

