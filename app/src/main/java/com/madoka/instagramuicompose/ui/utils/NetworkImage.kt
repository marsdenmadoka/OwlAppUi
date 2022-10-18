package com.madoka.instagramuicompose.ui.utils

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import coil.compose.AsyncImage
import com.madoka.mypokedexapp.ui.theme.compositedOnSurface


/**
 * A wrapper around [AsyncImage], setting a default [contentScale] and showing
 * content while loading.
 */
@Composable
fun NetworkImage(
    url: String,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Crop,
    placeholderColor: Color = MaterialTheme.colors.compositedOnSurface(0.2f)
) {
    AsyncImage(
        model = url,
        contentDescription = contentDescription,
        placeholder = ColorPainter(placeholderColor),
        modifier = modifier,
        contentScale = contentScale
    )
}