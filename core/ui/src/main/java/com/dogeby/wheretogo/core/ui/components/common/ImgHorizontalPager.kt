package com.dogeby.wheretogo.core.ui.components.common

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ImgHorizontalPager(
    imgSrcs: List<Any>,
    modifier: Modifier = Modifier,
    ratio: Float = 1.6f,
    onImgClick: ((page: Int, imgSrcs: List<Any>) -> Unit)? = null,
) {
    val pagerState = rememberPagerState {
        imgSrcs.size
    }
    var currentPage by remember {
        mutableIntStateOf(pagerState.currentPage)
    }
    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.currentPage }
            .collect { page ->
                currentPage = page
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
            AsyncImageWithFallback(
                imgSrc = imgSrcs[page],
                modifier = Modifier
                    .aspectRatio(ratio)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null,
                    ) {
                        if (onImgClick != null) {
                            onImgClick(currentPage, imgSrcs)
                        }
                    },
            )
        }
        PageTag(
            currentPage = { currentPage + 1 },
            totalPage = imgSrcs.size,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 16.dp, bottom = 12.dp)
                .alpha(0.75f),
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ImgHorizontalPagerPreview() {
    Surface {
        ImgHorizontalPager(
            imgSrcs = List(5) {
                "http://tong.visitkorea.or.kr/cms/resource/23/2678623_image2_1.jpg"
            },
        )
    }
}
