package com.dogeby.wheretogo.feature.reviewedit

import android.net.Uri
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddPhotoAlternate
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.dogeby.wheretogo.core.ui.R
import com.dogeby.wheretogo.core.ui.components.common.AsyncImageWithFallback

private val PhotoSize = 80.dp

@RequiresApi(30)
@Composable
internal fun PhotoPickerWithPickVisualMedia(
    photoSrcs: List<Uri>,
    maxItems: Int,
    onAddPhotoSrcs: (uris: List<Uri>) -> Unit,
    onRemovePhotoSrc: (uri: Uri) -> Unit,
) {
    val remainingPhotoCapacity = maxItems - photoSrcs.size
    val launcher = when {
        remainingPhotoCapacity > 1 -> {
            rememberLauncherForActivityResult(
                contract = ActivityResultContracts.PickMultipleVisualMedia(remainingPhotoCapacity),
                onResult = onAddPhotoSrcs,
            )
        }
        else -> {
            rememberLauncherForActivityResult(
                contract = ActivityResultContracts.PickVisualMedia(),
                onResult = {
                    it?.let {
                        onAddPhotoSrcs(listOf(it))
                    }
                },
            )
        }
    }

    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        item {
            val context = LocalContext.current
            PhotoPickerButton(
                onClick = {
                    if (remainingPhotoCapacity > 0) {
                        launcher.launch(
                            PickVisualMediaRequest(
                                ActivityResultContracts.PickVisualMedia.ImageOnly,
                            ),
                        )
                    } else {
                        Toast.makeText(
                            context,
                            context.getString(R.string.photo_picker_limit_warning, maxItems),
                            Toast.LENGTH_SHORT,
                        ).show()
                    }
                },
            )
        }
        items(photoSrcs) {
            PickedPhoto(
                uri = it,
                onRemove = onRemovePhotoSrc,
            )
        }
    }
}

@Composable
internal fun PhotoPickerWithGetContent(
    photoSrcs: List<Uri>,
    maxItems: Int,
    onAddPhotoSrc: (uri: Uri) -> Unit,
    onRemovePhotoSrc: (uri: Uri) -> Unit,
) {
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = {
            it?.let { onAddPhotoSrc(it) }
        },
    )

    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        item {
            val context = LocalContext.current
            PhotoPickerButton(
                onClick = {
                    if (photoSrcs.size < 5) {
                        launcher.launch("image/*")
                    } else {
                        Toast.makeText(
                            context,
                            context.getString(R.string.photo_picker_limit_warning, maxItems),
                            Toast.LENGTH_SHORT,
                        ).show()
                    }
                },
            )
        }
        items(photoSrcs) {
            PickedPhoto(
                uri = it,
                onRemove = onRemovePhotoSrc,
            )
        }
    }
}

@Composable
private fun PhotoPickerButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    shape: Shape = RoundedCornerShape(8),
    color: Color = MaterialTheme.colorScheme.surfaceContainerLow,
    contentColor: Color = MaterialTheme.colorScheme.onSurfaceVariant,
    borderColor: Color = MaterialTheme.colorScheme.onSurfaceVariant,
    tonalElevation: Dp = 0.dp,
    shadowElevation: Dp = 0.dp,
    size: Dp = PhotoSize,
) {
    Surface(
        onClick = onClick,
        modifier = modifier.size(size),
        enabled = enabled,
        shape = shape,
        color = color,
        contentColor = contentColor,
        tonalElevation = tonalElevation,
        shadowElevation = shadowElevation,
        border = BorderStroke(1.dp, borderColor),
    ) {
        Icon(
            imageVector = Icons.Default.AddPhotoAlternate,
            contentDescription = null,
            modifier = Modifier.padding(size / 4),
        )
    }
}

@Composable
private fun PickedPhoto(
    uri: Uri,
    onRemove: (uri: Uri) -> Unit,
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(8),
    size: Dp = PhotoSize,
) {
    Box(
        modifier = modifier,
    ) {
        AsyncImageWithFallback(
            imgSrc = uri,
            modifier = Modifier
                .size(size)
                .clip(shape)
                .align(Alignment.Center),
        )
        IconButton(
            onClick = { onRemove(uri) },
            modifier = Modifier
                .size(24.dp)
                .padding(4.dp)
                .align(Alignment.TopEnd),
            colors = IconButtonDefaults.iconButtonColors(
                containerColor = MaterialTheme.colorScheme.surface,
            ),
        ) {
            Icon(
                imageVector = Icons.Filled.Cancel,
                contentDescription = null,
                modifier = Modifier.size(16.dp),
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PhotoPickerPreview() {
    val photoSrcs = remember {
        mutableStateListOf<Uri>()
    }

    if (Build.VERSION.SDK_INT >= 30) {
        PhotoPickerWithPickVisualMedia(
            photoSrcs = photoSrcs,
            maxItems = 5,
            onAddPhotoSrcs = { uris ->
                photoSrcs.addAll(uris.filter { it !in photoSrcs })
            },
            onRemovePhotoSrc = {
                photoSrcs.remove(it)
            },
        )
    } else {
        PhotoPickerWithGetContent(
            photoSrcs = photoSrcs,
            maxItems = 5,
            onAddPhotoSrc = {
                if (it !in photoSrcs) {
                    photoSrcs.add(it)
                }
            },
            onRemovePhotoSrc = {
                photoSrcs.remove(it)
            },
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PhotoPickerButtonPreview() {
    PhotoPickerButton(
        onClick = {},
        modifier = Modifier.padding(16.dp),
    )
}

@Preview(showBackground = true)
@Composable
private fun PickedPhotoPreview() {
    PickedPhoto(
        uri = Uri.EMPTY,
        onRemove = {},
        modifier = Modifier.padding(16.dp),
    )
}
