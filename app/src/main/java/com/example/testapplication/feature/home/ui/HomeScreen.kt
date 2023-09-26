package com.example.testapplication.feature.home.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import com.example.testapplication.core.RequestResponse
import com.example.testapplication.core.extensions.printLog
import com.example.testapplication.feature.home.HomeViewModel
import com.example.testapplication.services.movies.repository.models.MoviesResponse
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.example.testapplication.core.extensions.launchActivity
import com.example.testapplication.core.search.TopBarSearch
import com.example.testapplication.core.search.Searcher
import com.example.testapplication.feature.home.ui.subviews.MoviesList
import com.example.testapplication.services.movies.repository.models.Result


@Composable
fun HomeScreen(viewModel: HomeViewModel) {

    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    var movies by remember { mutableStateOf<RequestResponse<MoviesResponse>?>(null) }
    var searchText by remember { mutableStateOf("") }


    fun fetchMovies() {
        viewModel.getMovies().observe(lifecycleOwner) { response ->
            when (response) {
                is RequestResponse.Success -> {
                    movies = response
                    "fetchMoviesSuccess".printLog(response)
                }

                is RequestResponse.Loading -> {

                }
                is RequestResponse.Error -> {
                    "fetchMoviesError".printLog(response.message)
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        fetchMovies()
    }

    fun searchCompareFunction(receivedObj: Result) = receivedObj.original_title
    var searcher: Searcher<Result>?
    val isSearchOpen = remember { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }

    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (topBarRef, listRef) = createRefs()
        TopBarSearch(
            modifier = Modifier.constrainAs(topBarRef) {
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                top.linkTo(parent.top)
                width = Dimension.fillToConstraints
            },
            title = "Popular Movies",
            isSearchOpen = isSearchOpen,
            searchable = !movies?.data?.results.isNullOrEmpty(),
            focusRequester = focusRequester,
            onTextSearched = {
                searchText = it
            }
        )

        Column(modifier = Modifier
            .constrainAs(listRef) {
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                top.linkTo(topBarRef.bottom)
                bottom.linkTo(parent.bottom)
                width = Dimension.fillToConstraints
                height = Dimension.fillToConstraints
            }
            .padding(horizontal = 20.dp, vertical = 20.dp)) {
            if (!movies?.data?.results.isNullOrEmpty()) {
                searcher =
                    movies?.data?.results?.let {
                        Searcher(
                            it.toMutableList(),
                            ::searchCompareFunction
                        )
                    }
                (searcher?.search(searchText) ?: movies?.data?.results?.toMutableList())?.let {
                    MoviesList(
                        movies = it,
                        modifier = Modifier,
                        onMovieClick =
                        {
                            context.launchActivity<MovieDescriptionActivity> {
                                putExtra(IntentConstants.movieID, it.id)
                            }
                        }

                    )
                }
            }

        }
    }


}


