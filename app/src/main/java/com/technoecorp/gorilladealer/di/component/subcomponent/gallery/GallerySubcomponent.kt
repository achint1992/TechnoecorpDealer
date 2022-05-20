package com.technoecorp.gorilladealer.di.component.subcomponent.gallery

import com.technoecorp.gorilladealer.ui.gallery.GalleryFragment
import dagger.Subcomponent

@GalleryScope
@Subcomponent(modules = [GalleryViewModelFactoryModule::class])
interface GallerySubcomponent {
    fun inject(galleryFragment: GalleryFragment)

    @Subcomponent.Factory
    interface Factory {
        fun create(): GallerySubcomponent
    }
}