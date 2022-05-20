package com.technoecorp.gorilladealer.di.component.subcomponent.dashboard

import com.technoecorp.gorilladealer.ui.dashboard.DashboardFragment
import dagger.Subcomponent

@DashboardScope
@Subcomponent(modules = [DashboardViewModelFactoryModule::class])
interface DashboardSubcomponent {

    fun inject(dashboardFragment: DashboardFragment)

    @Subcomponent.Factory
    interface Factory {
        fun create(): DashboardSubcomponent
    }
}