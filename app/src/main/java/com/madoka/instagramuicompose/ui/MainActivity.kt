package com.madoka.instagramuicompose.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
          // This app draws behind the system bars, so we want to handle fitting system windows
           // WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            OwlApp { finish() }
        }
    }
}
