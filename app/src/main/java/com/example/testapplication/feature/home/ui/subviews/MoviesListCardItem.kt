package com.example.testapplication.feature.home.ui.subviews


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.AsyncImage
import com.example.testapplication.core.extensions.addCardShadow
import com.example.testapplication.core.extensions.customClickable
import com.example.testapplication.services.movies.repository.models.Result


@Composable
fun MoviesListCardItem(
    item: Result,
    onMoviesClick: (Result) -> Unit,
) {
    if (!item.adult){
        Card(
            modifier = Modifier
                .padding(vertical = 10.dp)
                .height(70.dp)
                .addCardShadow()
                .clip(RoundedCornerShape(12.dp))
                .customClickable(onClick = {
                    onMoviesClick(item)
                }),
            shape = RoundedCornerShape(12.dp),
        ) {
            ConstraintLayout(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                val (imageRef, originalTitleRef, titleRef, overViewRef) = createRefs()
                Box(
                    modifier = Modifier
                        .constrainAs(imageRef) {
                            start.linkTo(parent.start, margin = 16.dp)
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                        }
                        .size(48.dp)
                        .clip(RoundedCornerShape(33.dp))
                        .background(color = Color.Gray),
                    contentAlignment = Alignment.Center
                ) {
                    AsyncImage(
                        modifier = Modifier,
                        model = item.poster_path,
                        contentDescription = null,
                    )

                }
                Text(
                    modifier = Modifier
                        .constrainAs(originalTitleRef) {
                            start.linkTo(imageRef.end, margin = 10.dp)
                            top.linkTo(parent.top, margin = 6.dp)
                        },
                    text = item.original_language,
                    fontSize = 11.sp,
                    color = Color.Gray
                )
                Text(
                    modifier = Modifier
                        .constrainAs(titleRef) {
                            start.linkTo(imageRef.end, margin = 10.dp)
                            top.linkTo(parent.top, margin = 0.dp)
                            bottom.linkTo(parent.bottom)
                        }
                        .width(170.dp),
                    text = item.original_title,
                    fontSize = 12.sp,
                    color = Color.Black,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    modifier = Modifier.constrainAs(overViewRef) {
                        start.linkTo(imageRef.end, margin = 10.dp)
                        bottom.linkTo(parent.bottom, margin = 5.dp)
                    },
                    text = item.release_date,
                    fontSize = 11.sp,
                    color = Color.Black,
                    maxLines = 1
                )
            }

        }
    }

}