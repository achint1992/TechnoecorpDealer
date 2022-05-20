package com.technoecorp.gorilladealer.di.component.subcomponent.profile

import com.technoecorp.gorilladealer.ui.profile.EditProfileFragment
import dagger.Subcomponent

@ProfileScope
@Subcomponent(modules = [ProfileViewModelFactoryModule::class])
interface ProfileSubComponent {
    fun inject(editProfileFragment: EditProfileFragment)

    @Subcomponent.Factory
    interface Factory {
        fun create(): ProfileSubComponent
    }
}