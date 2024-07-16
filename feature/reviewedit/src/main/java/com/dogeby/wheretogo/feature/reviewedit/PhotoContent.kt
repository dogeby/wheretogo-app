package com.dogeby.wheretogo.feature.reviewedit

import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import com.dogeby.wheretogo.core.ui.util.photoPicker
import com.dogeby.wheretogo.core.ui.util.rememberToast

private val PhotoSize = 80.dp

@Composable
internal fun PhotoContent(
    photoSrcs: List<Uri>,
    maxItems: Int,
    onAddPhotoSrcs: (uris: List<Uri>) -> Unit,
    onRemovePhotoSrc: (uri: Uri) -> Unit,
) {
    val remainingPhotoCapacity = maxItems - photoSrcs.size

    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        item {
            PhotoPickerButton(
                onPhotoSelected = onAddPhotoSrcs,
                maxItems = remainingPhotoCapacity,
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
    onPhotoSelected: (List<Uri>) -> Unit,
    modifier: Modifier = Modifier,
    maxItems: Int = 1,
    shape: Shape = RoundedCornerShape(8),
    color: Color = MaterialTheme.colorScheme.surfaceContainerLow,
    contentColor: Color = MaterialTheme.colorScheme.onSurfaceVariant,
    borderColor: Color = MaterialTheme.colorScheme.onSurfaceVariant,
    tonalElevation: Dp = 0.dp,
    shadowElevation: Dp = 0.dp,
    size: Dp = PhotoSize,
) {
    val context = LocalContext.current
    val toast = rememberToast()

    Surface(
        modifier = modifier
            .size(size)
            .then(
                if (maxItems > 0) {
                    Modifier.photoPicker(
                        onPhotoSelected = onPhotoSelected,
                        maxItems = maxItems,
                        interactionSource = remember { MutableInteractionSource() },
                    )
                } else {
                    Modifier.clickable {
                        toast(
                            context.getString(
                                R.string.photo_picker_limit_warning,
                                maxItems,
                            ),
                            Toast.LENGTH_SHORT,
                        )
                    }
                },
            ),
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
private fun PhotoContentPreview() {
    val photoSrcs = remember {
        mutableStateListOf<Uri>()
    }

    PhotoContent(
        photoSrcs = photoSrcs,
        maxItems = 5,
        onAddPhotoSrcs = { uris ->
            photoSrcs.addAll(uris.filter { it !in photoSrcs })
        },
        onRemovePhotoSrc = {
            photoSrcs.remove(it)
        },
    )
}

@Preview(showBackground = true)
@Composable
private fun PhotoPickerButtonPreview() {
    PhotoPickerButton(
        onPhotoSelected = {},
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
