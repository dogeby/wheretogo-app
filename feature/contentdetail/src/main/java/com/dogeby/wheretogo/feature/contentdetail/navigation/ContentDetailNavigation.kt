package com.dogeby.wheretogo.feature.contentdetail.navigation

import androidx.lifecycle.SavedStateHandle
import com.dogeby.wheretogo.core.common.decoder.StringDecoder

internal const val CONTENT_ID_ARG = "content_id"

internal class ContentDetailArgs(val contentId: String) {

    constructor(savedStateHandle: SavedStateHandle, stringDecoder: StringDecoder) :
        this(stringDecoder.decodeString(checkNotNull(savedStateHandle[CONTENT_ID_ARG])))
}
