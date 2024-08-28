package com.dogeby.wheretogo.core.domain.tour

import androidx.paging.PagingData
import androidx.paging.map
import com.dogeby.wheretogo.core.data.model.tour.ImgInfoData
import com.dogeby.wheretogo.core.data.repository.TourRepository
import com.dogeby.wheretogo.core.domain.model.tour.ImgInfo
import com.dogeby.wheretogo.core.domain.model.tour.toImgInfo
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetPagedImgInfoUseCase @Inject constructor(
    private val tourRepository: TourRepository,
) {

    operator fun invoke(contentId: String): Flow<PagingData<ImgInfo>> {
        return tourRepository.getPagedImgInfo(contentId).map { pagedImgInfo ->
            pagedImgInfo.map(ImgInfoData::toImgInfo)
        }
    }
}
