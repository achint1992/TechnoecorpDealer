package com.technoecorp.gorilladealer.ui.gallery

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.technoecorp.domain.ResultWrapper
import com.technoecorp.domain.domainmodel.request.GalleryDataRequest
import com.technoecorp.domain.domainmodel.response.company.gallery.GalleryResponse
import com.technoecorp.domain.usecase.company.GetGalleryDataUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class GalleryViewModel(private var getGalleryDataUseCase: GetGalleryDataUseCase) : ViewModel() {
    private var _gallery: MutableStateFlow<ResultWrapper<GalleryResponse>> =
        MutableStateFlow(ResultWrapper.Started())
    val gallery: StateFlow<ResultWrapper<GalleryResponse>> get() = _gallery

    fun getGalleryData(type: String) {
        viewModelScope.launch {
            _gallery.value = ResultWrapper.Loading()
            try {
                val result = getGalleryDataUseCase.execute(GalleryDataRequest(type))
                _gallery.value = result
            } catch (e: Exception) {
                _gallery.value = ResultWrapper.Error(e.message)
            }
        }
    }

}