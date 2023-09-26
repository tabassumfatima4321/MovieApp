package com.example.testapplication.feature.home.ui.subviews

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.testapplication.services.movies.repository.models.Result

@Composable
fun MoviesList(
    modifier: Modifier,
    onMovieClick: (Result) -> Unit,
    movies: List<Result>,
) {
    LazyColumn(modifier = modifier) {

        if (movies.isNotEmpty()) {
            items(movies) { movies ->
                MoviesListCardItem(
                    item = movies,
                    onMoviesClick = {
                        onMovieClick(it)
                    }
                )
            }
        }

        item {
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}