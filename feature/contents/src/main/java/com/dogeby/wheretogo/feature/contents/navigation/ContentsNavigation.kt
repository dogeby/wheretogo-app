package com.dogeby.wheretogo.feature.contents.navigation

import androidx.lifecycle.SavedStateHandle
import com.dogeby.wheretogo.core.common.decoder.StringDecoder

internal const val CONTENTS_CONTENT_TYPE_ID_ARG = "contents_content_type_id"
internal const val CONTENTS_AREA_CODE_ARG = "contents_area_code"

internal class ContentsArgs(
    val contentTypeId: String,
    val areaCode: String,
) {

    constructor(savedStateHandle: SavedStateHandle, stringDecoder: StringDecoder) :
        this(
            stringDecoder.decodeString(
                checkNotNull(savedStateHandle[CONTENTS_CONTENT_TYPE_ID_ARG]),
            ),
            stringDecoder.decodeString(
                checkNotNull(savedStateHandle[CONTENTS_AREA_CODE_ARG]),
            ),
        )
}
