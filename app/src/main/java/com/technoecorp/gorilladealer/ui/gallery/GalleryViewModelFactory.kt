package com.technoecorp.gorilladealer.ui.gallery

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.technoecorp.domain.usecase.company.GetGalleryDataUseCase
import java.lang.IllegalArgumentException

class GalleryViewModelFactory(private var getGalleryDataUseCase: GetGalleryDataUseCase) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GalleryViewModel::class.java)) {
            return GalleryViewModel(getGalleryDataUseCase) as T
        }
        throw IllegalArgumentException("Invalid View Model")
    }
}