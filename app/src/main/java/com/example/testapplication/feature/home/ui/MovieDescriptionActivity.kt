package com.example.testapplication.feature.home.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import com.example.testapplication.feature.home.MovieDescriptionViewModel
import com.example.testapplication.ui.theme.TestApplicationTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MovieDescriptionActivity : ComponentActivity() {

    private val viewModel: MovieDescriptionViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TestApplicationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    MoviesDescriptionScreen(
                        movieID = intent.getIntExtra(IntentConstants.movieID, 0),
                        viewModel = viewModel
                    )
                }
            }
        }
    }
}

