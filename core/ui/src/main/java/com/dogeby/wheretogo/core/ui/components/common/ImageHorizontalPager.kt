package com.dogeby.wheretogo.core.ui.components.common

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.pager.PagerSnapDistance
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.dogeby.wheretogo.core.ui.R

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ImageHorizontalPager(
    images: List<String>,
    modifier: Modifier = Modifier,
    ratio: Float = 1.5f,
) {
    val pagerState = rememberPagerState {
        images.size
    }
    var currentPage by remember {
        mutableIntStateOf(pagerState.currentPage + 1)
    }
    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.currentPage }
            .collect { page ->
                currentPage = page + 1
            }
    }

    Box(modifier = modifier) {
        HorizontalPager(
            state = pagerState,
            flingBehavior = PagerDefaults.flingBehavior(
                state = pagerState,
                pagerSnapDistance = PagerSnapDistance.atMost(1),
            ),
        ) { page ->
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(images[page])
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                imageLoader = ImageLoader(LocalContext.current),
                modifier = Modifier.aspectRatio(ratio),
                contentScale = ContentScale.FillBounds,
                placeholder = painterResource(id = R.drawable.loading_img),
                error = painterResource(id = R.drawable.ic_broken_image),
            )
        }
        PageTag(
            currentPage = { currentPage },
            totalPage = images.size,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 16.dp, bottom = 12.dp)
                .alpha(0.75f),
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ImageHorizontalPagerPreview() {
    Surface {
        ImageHorizontalPager(
            images = List(5) {
                "http://tong.visitkorea.or.kr/cms/resource/23/2678623_image2_1.jpg"
            },
        )
    }
}
