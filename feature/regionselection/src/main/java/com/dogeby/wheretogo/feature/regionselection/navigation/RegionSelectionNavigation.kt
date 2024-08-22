package com.dogeby.wheretogo.feature.regionselection.navigation

import androidx.lifecycle.SavedStateHandle
import com.dogeby.wheretogo.core.common.decoder.StringDecoder

internal const val CONTENT_TYPE_ID_ARG = "content_type_id"

internal class RegionSelectionArgs(val contentTypeId: String) {

    constructor(savedStateHandle: SavedStateHandle, stringDecoder: StringDecoder) :
        this(stringDecoder.decodeString(checkNotNull(savedStateHandle[CONTENT_TYPE_ID_ARG])))
}
