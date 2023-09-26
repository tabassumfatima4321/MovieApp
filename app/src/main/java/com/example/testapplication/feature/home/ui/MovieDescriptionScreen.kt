package com.example.testapplication.feature.home.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.AsyncImage
import com.example.testapplication.core.RequestResponse
import com.example.testapplication.core.extensions.printLog
import com.example.testapplication.feature.home.MovieDescriptionViewModel
import com.example.testapplication.services.movies.repository.models.MovieDescriptionResponse


@Composable
fun MoviesDescriptionScreen(movieID: Int, viewModel: MovieDescriptionViewModel) {


    val lifecycleOwner = LocalLifecycleOwner.current

    var movieDescription by remember {
        mutableStateOf<RequestResponse<MovieDescriptionResponse>?>(
            null
        )
    }


    fun fetchMovieById() {
        viewModel.getMoviesDescription(movieId = movieID).observe(lifecycleOwner) { response ->
            when (response) {
                is RequestResponse.Success -> {
                    movieDescription = response
                    "fetchMovieDescriptionSuccess".printLog(response)
                }

                is RequestResponse.Loading -> {

                }
                is RequestResponse.Error -> {
                    "fetchMovieDescriptionError".printLog(response.message)
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        fetchMovieById()
    }


    ConstraintLayout(
        modifier = Modifier
            .fillMaxHeight()
    ) {
        val (imageRef, originalTitleRef , overViewRef) = createRefs()

        AsyncImage(
            modifier = Modifier
                .background(Color.Green)
                .constrainAs(imageRef) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
                }
                .fillMaxWidth()
                .height(300.dp),
            model = movieDescription?.data?.poster_path.orEmpty(),
            contentDescription = null
        )

        Text(
            modifier = Modifier
                .constrainAs(originalTitleRef) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(imageRef.bottom, margin = 20.dp)
                },
            text = movieDescription?.data?.original_title.orEmpty(),
            fontSize = 24.sp,
            color = Color.Black
        )

        Text(
            modifier = Modifier
                .padding(start = 25.dp , end = 10.dp)
                .constrainAs(overViewRef) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(originalTitleRef.bottom, margin = 20.dp)
                },
            text = "Movie OverView:\n\n${movieDescription?.data?.overview.orEmpty()}",
            fontSize = 20.sp,
            color = Color.Black,
            maxLines = 9,
            overflow = TextOverflow.Ellipsis,
        )
    }

}