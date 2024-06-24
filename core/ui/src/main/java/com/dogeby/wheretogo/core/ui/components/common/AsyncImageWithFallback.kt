package com.dogeby.wheretogo.core.ui.components.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.requiredSizeIn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.dogeby.wheretogo.core.ui.R

@Composable
fun AsyncImageWithFallback(
    imgSrc: Any,
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
    imageLoader: ImageLoader = ImageLoader(LocalContext.current),
    contentScale: ContentScale = ContentScale.FillBounds,
) {
    SubcomposeAsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(imgSrc)
            .crossfade(true)
            .build(),
        contentDescription = contentDescription,
        imageLoader = imageLoader,
        modifier = modifier,
        contentScale = contentScale,
        loading = {
            Box(contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        },
        error = {
            Surface(
                color = MaterialTheme.colorScheme.errorContainer,
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_broken_image),
                    contentDescription = null,
                    modifier = Modifier.requiredSizeIn(maxWidth = 120.dp),
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onErrorContainer),
                )
            }
        },
    )
}

@Preview(showBackground = true)
@Composable
private fun AsyncImageWithFallbackPreview() {
    AsyncImageWithFallback(
        imgSrc = "http://tong.visitkorea.or.kr/cms/resource/23/2678623_image2_1.jpg",
        modifier = Modifier.aspectRatio(1.5f),
    )
}

@Preview(showBackground = true)
@Composable
private fun AsyncImageWithFallbackPreview_Error() {
    AsyncImageWithFallback(
        imgSrc = "",
        modifier = Modifier.aspectRatio(1.5f),
    )
}
