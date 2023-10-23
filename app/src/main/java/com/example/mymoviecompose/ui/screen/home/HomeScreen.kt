package com.example.mymoviecompose.ui.screen.home

import android.widget.Toast
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.mymoviecompose.R
import com.example.mymoviecompose.data.network.response.ResultsItem
import com.example.mymoviecompose.data.network.response.ResultsItemTrending
import com.example.mymoviecompose.ui.common.HomeUiState
import com.example.mymoviecompose.ui.component.ErrorScreen
import com.example.mymoviecompose.ui.component.HomeSection
import com.example.mymoviecompose.ui.component.LoadingScreen
import com.example.mymoviecompose.ui.component.MovieItemColumn
import com.example.mymoviecompose.ui.component.MovieItemRow
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    uiState: HomeUiState,
    modifier: Modifier = Modifier,
    homeViewModel: HomeViewModel = viewModel(factory = HomeViewModel.Factory),
    navigateToDetail: (Int) -> Unit,
    navigateToSetting: () -> Unit,
    scrollBehavior: TopAppBarScrollBehavior
) {
    Scaffold(
        topBar = {
            HomeScreenTopAppBar(
                scrollBehavior = scrollBehavior,
                navigateToSetting = navigateToSetting
            )
        }
    ) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            when (uiState) {
                is HomeUiState.Loading -> LoadingScreen()
                is HomeUiState.Success -> HomeContent(
                    movie = uiState.movie.results,
                    movieTrending = uiState.trendingMovie.resultsTrending,
                    navigateToDetail = navigateToDetail,
                    modifier = modifier
                )

                is HomeUiState.Error -> ErrorScreen(
                    modifier = Modifier.clickable {
                        homeViewModel.getMovies()
                    })
            }
        }
    }
}

@Composable
fun HomeContent(
    movie: List<ResultsItem>,
    movieTrending: List<ResultsItemTrending>,
    navigateToDetail: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    Column(
        modifier = modifier
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        MoviePopularBannerShow(movie = movie)
        Spacer(modifier = Modifier.height(12.dp))
        HomeSection(
            title = stringResource(R.string.trending_today),
            modifier = Modifier.clickable {
                Toast.makeText(
                    context,
                    "Under developing",
                    Toast.LENGTH_SHORT
                ).show()
            }) {
            MovieListRowPopularToday(
                movie = movieTrending,
                navigateToDetail = navigateToDetail
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        HomeSection(title = stringResource(R.string.discover_movie)) {
            MovieListColumnDiscover(
                movie = movie,
                navigateToDetail = navigateToDetail
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun MovieListColumnDiscover(
    movie: List<ResultsItem>,
    navigateToDetail: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyHorizontalGrid(
        rows = GridCells.Fixed(3),
        modifier = Modifier.height(300.dp)
    ) {
        items(movie, key = { it.id }) { data ->
            MovieItemColumn(movie = data,
                modifier = modifier
                    .clickable { navigateToDetail(data.id) }
            )
        }
    }
}

@Composable
fun MovieListRowPopularToday(
    movie: List<ResultsItemTrending>,
    navigateToDetail: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = modifier
    ) {
        items(movie, key = { it.id }) { data ->
            MovieItemRow(movie = data,
                modifier = modifier
                    .clickable { navigateToDetail(data.id) }
            )
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun MoviePopularBannerShow(
    movie: List<ResultsItem>,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val pagerState = rememberPagerState(pageCount = movie.size)

    LaunchedEffect(key1 = Unit) {
        while (true) {
            delay(2000)
            val nextPage = (pagerState.currentPage + 1) % pagerState.pageCount
            pagerState.scrollToPage(nextPage)
        }
    }

    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            HorizontalPager(
                state = pagerState,
                modifier = Modifier.fillMaxSize()
            ) { currentPage ->
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.BottomStart
                ) {
                    Card(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        elevation = CardDefaults.cardElevation(8.dp)
                    ) {
                        AsyncImage(
                            model = ImageRequest.Builder(context)
                                .data("https://image.tmdb.org/t/p/original/${movie[currentPage].backdropPath}")
                                .crossfade(true)
                                .build(),
                            contentDescription = "image"
                        )
                    }
                    Text(
                        text = movie[currentPage].title, // Replace with the actual title property
                        color = Color.White,
                        modifier = Modifier
                            .padding(16.dp)
                            .background(Color.Black.copy(0.6f))
                            .padding(8.dp)
                            .fillMaxWidth()

                    )

                }
            }
        }
        PageIndicator(
            pageCount = movie.size,
            currentPage = pagerState.currentPage,
            modifier = modifier
        )
    }
}

@Composable
fun PageIndicator(pageCount: Int, currentPage: Int, modifier: Modifier) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        repeat(pageCount) {
            IndicatorDots(isSelected = it == currentPage)
        }
    }
}

@Composable
fun IndicatorDots(isSelected: Boolean, modifier: Modifier = Modifier) {
    val size = animateDpAsState(targetValue = if (isSelected) 12.dp else 10.dp, label = "")
    Box(
        modifier = modifier
            .padding(2.dp)
            .size(size.value)
            .clip(CircleShape)
            .background(if(isSelected) Color(0XFF373737 ) else Color(0x68727272))
    )


}

@ExperimentalMaterial3Api
@Composable
fun HomeScreenTopAppBar(
    scrollBehavior: TopAppBarScrollBehavior,
    modifier: Modifier = Modifier,
    navigateToSetting: () -> Unit
) {
    CenterAlignedTopAppBar(
        scrollBehavior = scrollBehavior,
        title = {
            Text(
                text = stringResource(R.string.movie_app),
                style = MaterialTheme.typography.headlineSmall,
            )
        },
        actions = {
            IconButton(onClick = { navigateToSetting() }) {
                Icon(imageVector = Icons.Default.Settings, contentDescription = null)
            }
        },
        modifier = modifier
    )
}