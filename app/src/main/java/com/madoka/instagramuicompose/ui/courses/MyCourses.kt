package com.madoka.instagramuicompose.ui.courses

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp

@Composable
fun   MyCourses(message: String, from: String) {
    Column {
        Text(
            text = message,
            fontSize = 36.sp,
        )
        Text(
            text = from,
            fontSize = 24.sp,
        )
    }
}

@Preview(showBackground = false)
@Composable
fun  MyCourses() {
    MyCourses ("my courses!", "- from Emma")
}
