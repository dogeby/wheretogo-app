package com.dogeby.wheretogo.core.ui.components.dialogue

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.pager.PagerSnapDistance
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.dogeby.wheretogo.core.ui.components.common.AsyncImageWithFallback
import net.engawapg.lib.zoomable.rememberZoomState
import net.engawapg.lib.zoomable.zoomable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImgDetailDialogue(
    imgSrcs: List<String>,
    onDismissRequest: () -> Unit,
) {
    var page by remember {
        mutableIntStateOf(1)
    }
    var topBarVisible by remember {
        mutableStateOf(true)
    }
    val animatedAlpha by animateFloatAsState(
        targetValue = if (topBarVisible) 1.0f else 0f,
        label = "ImgDetailDialogueTopBarAlpha",
    )
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(usePlatformDefaultWidth = false),
    ) {
        Surface(
            modifier = Modifier
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() },
                ) {
                    topBarVisible = !topBarVisible
                },
        ) {
            ImgDetailHorizontalPager(
                imgSrcs = imgSrcs,
                onPageChanged = {
                    page = it
                },
            )
            CenterAlignedTopAppBar(
                title = {
                    Text(text = "$page / ${imgSrcs.size}")
                },
                modifier = Modifier.graphicsLayer {
                    alpha = animatedAlpha
                },
                navigationIcon = {
                    IconButton(onClick = {
                        if (topBarVisible) {
                            onDismissRequest()
                        }
                    }) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = null,
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.Transparent,
                ),
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ImgDetailHorizontalPager(
    imgSrcs: List<String>,
    onPageChanged: (page: Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    val pagerState = rememberPagerState {
        imgSrcs.size
    }
    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.currentPage }
            .collect { page ->
                onPageChanged(page + 1)
            }
    }
    HorizontalPager(
        state = pagerState,
        modifier = modifier.fillMaxSize(),
        flingBehavior = PagerDefaults.flingBehavior(
            state = pagerState,
            pagerSnapDistance = PagerSnapDistance.atMost(1),
        ),
    ) { page ->
        val zoomState = rememberZoomState()

        AsyncImageWithFallback(
            imgSrc = imgSrcs[page],
            modifier = Modifier
                .fillMaxSize()
                .zoomable(
                    zoomState = zoomState,
                    enableOneFingerZoom = false,
                ),
            contentScale = ContentScale.Fit,
        )

        LaunchedEffect(pagerState.settledPage) {
            zoomState.reset()
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ImgDetailDialoguePreview() {
    ImgDetailDialogue(
        imgSrcs = List(5) {
            "http://tong.visitkorea.or.kr/cms/resource/23/2678623_image2_1.jpg"
        },
        onDismissRequest = {},
    )
}

@Preview(showBackground = true)
@Composable
private fun ImgDetailHorizontalPagerPreview() {
    ImgDetailHorizontalPager(
        imgSrcs = List(5) {
            "http://tong.visitkorea.or.kr/cms/resource/23/2678623_image2_1.jpg"
        },
        onPageChanged = {},
    )
}
