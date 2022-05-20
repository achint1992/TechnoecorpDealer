package com.technoecorp.gorilladealer.di.component.subcomponent.main

import com.technoecorp.data.repository.preference.PreferencesRepository
import com.technoecorp.gorilladealer.ui.main.SharedViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class SharedViewModelFactoryModule {

    @MainScope
    @Provides
    fun providesSharedViewModelFactory(preferencesDatastore: PreferencesRepository): SharedViewModelFactory {
        return SharedViewModelFactory(preferencesDatastore)
    }
}