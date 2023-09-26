package com.example.testapplication.core.search

import androidx.compose.animation.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.example.testapplication.core.extensions.addCardShadow
import com.example.testapplication.core.extensions.customClickable
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun TopBarSearch(
    modifier: Modifier = Modifier,
    title: String,
    showTitle: Boolean = true,
    searchable: Boolean = true,
    isSearchOpen: MutableState<Boolean>,
    focusRequester: FocusRequester,
    onTextSearched: (String) -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()
    val text = remember { mutableStateOf("") }
    fun resetSearchBar() {
        coroutineScope.launch {
            delay(100)
            if (!isSearchOpen.value) {
                text.value = ""
                onTextSearched("")
            }
            if (isSearchOpen.value) focusRequester.requestFocus()
        }
    }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .addCardShadow()
            .clip(RoundedCornerShape(bottomEnd = 2.dp, bottomStart = 2.dp)),
        shape = RoundedCornerShape(bottomEnd = 2.dp, bottomStart = 2.dp),
        elevation = 0.dp
    ) {
        ConstraintLayout(modifier = Modifier.fillMaxWidth()) {
            val (backIconRef, titleLayoutRef, searchIconRef, searchLayoutRef) = createRefs()
            if (!isSearchOpen.value) {
                Column(
                    modifier = Modifier.constrainAs(titleLayoutRef) {
                        top.linkTo(parent.top, margin = 10.dp)
                        bottom.linkTo(parent.bottom, margin = 10.dp)
                        start.linkTo(parent.start , margin = 60.dp)
                        end.linkTo(searchIconRef.start, margin = 20.dp)
                        width = Dimension.fillToConstraints
                    }, horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    if (title.isNotBlank() && showTitle) {
                        Text(
                            modifier = Modifier,
                            text = title,
                            fontSize = 18.sp,
                            color = Color.Black,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
            Column(modifier = Modifier
                .fillMaxWidth()
                .height(45.dp)
                .constrainAs(searchLayoutRef) {
                    top.linkTo(parent.top, margin = 15.dp)
                    start.linkTo(parent.start, margin = 20.dp)
                    end.linkTo(searchIconRef.start, margin = 15.dp)
                    bottom.linkTo(parent.bottom, margin = 15.dp)
                    width = Dimension.fillToConstraints
                }) {
                AnimatedVisibility(
                    visible = isSearchOpen.value,
                    enter = fadeIn() + expandHorizontally(),
                    exit = fadeOut() + shrinkHorizontally(),
                ) {
                    PrimaryTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(45.dp)
                            .focusRequester(focusRequester),
                        backgroundColor = Color.LightGray,
                        value = text.value,
                        placeholder = {
                            Text(
                                text = "Search",
                                fontSize = 14.sp,
                                color = Color.Gray
                            )
                        },
                        onValueChange = {
                            text.value = it
                            if (isSearchOpen.value) {
                                onTextSearched(text.value)
                            }
                        },
                        singleLine = true,
                        shape = RoundedCornerShape(12.dp),
                        leadingIcon = {
                            Image(
                                modifier = Modifier
                                    .padding(start = 15.dp, end = 15.dp)
                                    .customClickable(onClick = {
                                        isSearchOpen.value = !isSearchOpen.value
                                        resetSearchBar()
                                    }),
                                painter = painterResource(id = com.example.testapplication.R.drawable.ic_back),
                                contentDescription = null
                            )
                        },
                        textStyle = TextStyle(
                            fontWeight = FontWeight.Normal,
                            fontSize = 14.sp,
                            color = Color.Black
                        ),
                    )
                }
            }

            Box(
                modifier = Modifier.constrainAs(searchIconRef) {
                    top.linkTo(parent.top, margin = 20.dp)
                    bottom.linkTo(parent.bottom, margin = 20.dp)
                    end.linkTo(parent.end, margin = 15.dp)
                },
                contentAlignment = Alignment.Center
            ) {
                if (searchable) {
                    PrimaryIconButton(
                        icon = com.example.testapplication.R.drawable.ic_baseline_search_24,
                        onClick = {
                            if (!isSearchOpen.value) isSearchOpen.value = true
                            resetSearchBar()
                        }
                    )
                }
            }
        }
    }
}