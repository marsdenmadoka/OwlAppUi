package com.madoka.instagramuicompose

import android.app.Application
import coil.ImageLoader
import coil.ImageLoaderFactory
import com.madoka.instagramuicompose.ui.utils.UnsplashSizingInterceptor


@Suppress("unused")
class OwlApplication : Application(), ImageLoaderFactory {

    /**
     * Create the singleton [ImageLoader].
     * This is used by [AsyncImage] to load images in the app.
     */
    override fun newImageLoader(): ImageLoader {
        return ImageLoader.Builder(this)
            .components {
                add(UnsplashSizingInterceptor)
            }
            // Ignore the Unsplash cache headers as they set `Cache-Control:must-revalidate` which
            // requires a network operation even if the image is cached locally.
            .respectCacheHeaders(false)
            .build()
    }
}