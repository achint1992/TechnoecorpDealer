package com.technoecorp.gorilladealer.di.component.subcomponent.main

import com.technoecorp.gorilladealer.ui.dashboard.DashboardActivity
import com.technoecorp.gorilladealer.ui.main.MainFragment
import dagger.Subcomponent

@MainScope
@Subcomponent(modules = [SharedViewModelFactoryModule::class])
interface MainSubcomponent {
    fun inject(mainFragment: MainFragment)

    fun injectToDashboard(dashboardActivity: DashboardActivity)


    @Subcomponent.Factory
    interface Factory {
        fun create(): MainSubcomponent
    }
}