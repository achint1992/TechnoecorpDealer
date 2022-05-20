package com.technoecorp.gorilladealer.di.component.subcomponent.gallery

import com.technoecorp.domain.usecase.company.GetGalleryDataUseCase
import com.technoecorp.gorilladealer.ui.gallery.GalleryViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class GalleryViewModelFactoryModule {

    @GalleryScope
    @Provides
    fun providesGalleryViewModelFactory(getGalleryDataUseCase: GetGalleryDataUseCase)
            : GalleryViewModelFactory {
        return GalleryViewModelFactory(getGalleryDataUseCase)
    }
}