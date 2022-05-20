package com.technoecorp.domain.usecase.company

import com.technoecorp.domain.ResultWrapper
import com.technoecorp.domain.domainmodel.request.GalleryDataRequest
import com.technoecorp.domain.domainmodel.response.company.gallery.GalleryResponse
import com.technoecorp.domain.repository.ICompanyDataRepository

class GetGalleryDataUseCase(private val companyDataRepository: ICompanyDataRepository) {
    suspend fun execute(galleryDataRequest: GalleryDataRequest) : ResultWrapper<GalleryResponse> =
        companyDataRepository.getGalleryData(galleryDataRequest)
}