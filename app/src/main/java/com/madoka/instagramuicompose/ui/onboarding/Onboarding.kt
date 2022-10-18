package com.madoka.instagramuicompose.ui.onboarding

import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.Image
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.rounded.Explore
import androidx.compose.material.icons.rounded.ExploreOff
import androidx.compose.material.icons.rounded.ImageSearch
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.madoka.instagramuicompose.R
import com.madoka.instagramuicompose.ui.model.Topic
import com.madoka.instagramuicompose.ui.model.topics
import com.madoka.instagramuicompose.ui.theme.OwlTheme
import com.madoka.instagramuicompose.ui.theme.YellowTheme
import com.madoka.instagramuicompose.ui.utils.NetworkImage
import com.madoka.mypokedexapp.ui.theme.pink500
import java.util.Collections.max
import kotlin.math.max

//Onboarding section The onboarding screen allows users to customize their experience by selecting topics.
@Composable
fun Onboarding(onboardingComplete: () -> Unit) {
    YellowTheme {
        Scaffold(topBar = { AppBar() },
            backgroundColor = MaterialTheme.colors.primarySurface,
            floatingActionButton = {
                FloatingActionButton(
                    onClick = onboardingComplete,
                 modifier = Modifier.navigationBarsPadding()
                ) {
                    Icon(
                      imageVector = Icons.Rounded.Explore,
                        contentDescription = "Continue to courses"//stringResource(R.string.label_continue_to_courses)
                    )
                }
            }
        )
        { innerPadding ->
            Column(modifier = Modifier
                .statusBarsPadding()
                .navigationBarsPadding()
                .padding(innerPadding)
            ) {
                Text(text = "Choose topics that interest you",
                    style = MaterialTheme.typography.h4,
                    textAlign = TextAlign.End,
                    modifier = Modifier.padding(
                        horizontal = 16.dp,
                        vertical = 32.dp
                    )
                )
                TopicsGrid()
                Spacer(Modifier.height(56.dp))
            }

            }
        }
    }


@Composable
private fun AppBar() {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding()
    ) {
        Image(
            painter = painterResource(id = OwlTheme.images.lockupLogo),
            contentDescription = null,
            modifier = Modifier.padding(16.dp)
        )
        IconButton(
            modifier = Modifier.padding(16.dp),
            onClick = { /* todo */ }
        ) {
            Icon(
                imageVector = Icons.Filled.Settings,
                contentDescription ="Settings" //stringResource(R.string.label_settings)
            )
        }
    }
}

@Composable
private fun TopicsGrid(modifier: Modifier = Modifier) {
    StaggeredGrid(
        modifier = modifier
            .horizontalScroll(rememberScrollState())
            .padding(horizontal = 8.dp)
    ) {
        topics.forEach { topic ->
            TopicChip(topic = topic)
        }
    }
}




//selection animation
private enum class SelectionState { Unselected, Selected }

/**
 * Class holding animating values when transitioning topic chip states.
 */

private class TopicChipTransition(
    cornerRadius: State<Dp>,
    selectedAlpha: State<Float>,
    checkScale: State<Float>
) {
    val cornerRadius by cornerRadius
    val selectedAlpha by selectedAlpha
    val checkScale by checkScale
}

@Composable
private fun topicChipTransition(topicSelected: Boolean): TopicChipTransition {
    val transition = updateTransition(
        targetState = if (topicSelected) SelectionState.Selected else SelectionState.Unselected
    )
    val cornerRadius = transition.animateDp { state ->
        when (state) {
            SelectionState.Unselected -> 0.dp
            SelectionState.Selected -> 28.dp
        }
    }
    val selectedAlpha = transition.animateFloat { state ->
        when (state) {
            SelectionState.Unselected -> 0f
            SelectionState.Selected -> 0.8f
        }
    }
    val checkScale = transition.animateFloat { state ->
        when (state) {
            SelectionState.Unselected -> 0.6f
            SelectionState.Selected -> 1f
        }
    }
    return remember(transition) {
        TopicChipTransition(cornerRadius, selectedAlpha, checkScale)
    }
}

//Topic chip
@Composable
private fun TopicChip(topic: Topic) {
    val (selected, onSelected) = remember { mutableStateOf(false) }
    val topicChipTransitionState = topicChipTransition(selected)

    Surface(
        modifier = Modifier.padding(4.dp),
        elevation = OwlTheme.elevations.card,
        shape = MaterialTheme.shapes.medium.copy(
            topStart = CornerSize(
                topicChipTransitionState.cornerRadius
            )
        )
    ) {
        Row(modifier = Modifier.toggleable(value = selected, onValueChange = onSelected)) {
            Box {
                NetworkImage(
                    url = OwlTheme.images.lockupLogo.toString(),//topic.imageUrl,//OwlTheme.images.lockupLogo.toString()  ,
                    contentDescription = null,
                    modifier = Modifier
                        .size(width = 72.dp, height = 72.dp)
                        .aspectRatio(1f)
                )
                if (topicChipTransitionState.selectedAlpha > 0f) {
                    Surface(
                        color = pink500.copy(alpha = topicChipTransitionState.selectedAlpha),
                        modifier = Modifier.matchParentSize()
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Done,
                            contentDescription = null,
                            tint = MaterialTheme.colors.onPrimary.copy(
                                alpha = topicChipTransitionState.selectedAlpha
                            ),
                            modifier = Modifier
                                .wrapContentSize()
                                .scale(topicChipTransitionState.checkScale)
                        )
                    }
                }
            }
            Column {
                Text(
                    text = topic.name,
                    style = MaterialTheme.typography.body1,
                    modifier = Modifier.padding(
                        start = 16.dp,
                        top = 16.dp,
                        end = 16.dp,
                        bottom = 8.dp
                    )
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                        Icon(
                            painter = painterResource(R.drawable.ic_grain),
                            contentDescription = null,
                            modifier = Modifier
                                .padding(start = 16.dp)
                                .size(12.dp)
                        )
                        Text(
                            text = topic.courses.toString(),
                            style = MaterialTheme.typography.caption,
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }
                }
            }
        }
    }
}


//staggered grid layout
@Composable
private fun StaggeredGrid(
    modifier: Modifier = Modifier,
    rows: Int = 3,
    content: @Composable () -> Unit
) {
    Layout(
        content = content,
        modifier = modifier
    ) { measurables, constraints ->
        val rowWidths = IntArray(rows) { 0 } // Keep track of the width of each row
        val rowHeights = IntArray(rows) { 0 } // Keep track of the height of each row

        // Don't constrain child views further, measure them with given constraints
        val placeables = measurables.mapIndexed { index, measurable ->
            val placeable = measurable.measure(constraints)

            // Track the width and max height of each row
            val row = index % rows
            rowWidths[row] += placeable.width
            rowHeights[row] = max(rowHeights[row], placeable.height)

            placeable
        }

        // Grid's width is the widest row
        val width = rowWidths.maxOrNull()?.coerceIn(constraints.minWidth, constraints.maxWidth)
            ?: constraints.minWidth
        // Grid's height is the sum of each row
        val height = rowHeights.sum().coerceIn(constraints.minHeight, constraints.maxHeight)

        // y co-ord of each row
        val rowY = IntArray(rows) { 0 }
        for (i in 1 until rows) {
            rowY[i] = rowY[i - 1] + rowHeights[i - 1]
        }
        layout(width, height) {
            // x co-ord we have placed up to, per row
            val rowX = IntArray(rows) { 0 }
            placeables.forEachIndexed { index, placeable ->
                val row = index % rows
                placeable.place(
                    x = rowX[row],
                    y = rowY[row]
                )
                rowX[row] += placeable.width
            }
        }
    }
}




@Preview(name = "Onboarding")
@Composable
private fun OnboardingPreview() {
    Onboarding(onboardingComplete = { })
}

@Preview("Topic Chip")
@Composable
private fun TopicChipPreview() {
    YellowTheme {
        TopicChip(topics.first())
    }
}
