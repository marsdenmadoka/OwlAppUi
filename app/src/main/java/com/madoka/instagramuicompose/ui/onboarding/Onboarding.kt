package com.madoka.instagramuicompose.ui.onboarding

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Explore
import androidx.compose.material.icons.rounded.ExploreOff
import androidx.compose.material.icons.rounded.ImageSearch
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.madoka.instagramuicompose.ui.theme.YellowTheme

//Onboarding section The onboarding screen allows users to customize their experience by selecting topics.
@Composable
fun Onboarding(onboardingComplete: () -> Unit) {
    YellowTheme {
        Scaffold(topBar = { },
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
                TopicsGrid(

                )
                Spacer(Modifier.height(56.dp))
            }

            }
        }
    }


@Composable
