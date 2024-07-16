package com.dogeby.wheretogo.core.ui.util

import android.net.Uri
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed

fun Modifier.photoPicker(
    onPhotoSelected: (List<Uri>) -> Unit,
    maxItems: Int = 1,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource,
) = if (Build.VERSION.SDK_INT >= 30) {
    photoPickerWithPickVisualMedia(
        maxItems = maxItems,
        onPhotoSelected = onPhotoSelected,
        interactionSource = interactionSource,
        enabled = enabled,
    )
} else {
    photoPickerWithGetContent(
        onPhotoSelected = {
            onPhotoSelected(listOf(it))
        },
        interactionSource = interactionSource,
        enabled = enabled,
    )
}

@RequiresApi(30)
private fun Modifier.photoPickerWithPickVisualMedia(
    maxItems: Int,
    onPhotoSelected: (List<Uri>) -> Unit,
    interactionSource: MutableInteractionSource,
    enabled: Boolean = true,
) = composed {
    val launcher = when {
        maxItems > 1 -> {
            rememberLauncherForActivityResult(
                contract = ActivityResultContracts.PickMultipleVisualMedia(maxItems),
                onResult = onPhotoSelected,
            )
        }
        else -> {
            rememberLauncherForActivityResult(
                contract = ActivityResultContracts.PickVisualMedia(),
                onResult = {
                    it?.let {
                        onPhotoSelected(listOf(it))
                    }
                },
            )
        }
    }

    this.clickable(
        indication = LocalIndication.current,
        interactionSource = interactionSource,
        enabled = enabled,
        onClick = {
            if (maxItems > 0) {
                launcher.launch(
                    PickVisualMediaRequest(
                        ActivityResultContracts.PickVisualMedia.ImageOnly,
                    ),
                )
            }
        },
    )
}

private fun Modifier.photoPickerWithGetContent(
    onPhotoSelected: (Uri) -> Unit,
    interactionSource: MutableInteractionSource,
    enabled: Boolean = true,
) = composed {
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = {
            it?.let { onPhotoSelected(it) }
        },
    )

    this.clickable(
        indication = LocalIndication.current,
        interactionSource = interactionSource,
        enabled = enabled,
        onClick = {
            launcher.launch("image/*")
        },
    )
}
