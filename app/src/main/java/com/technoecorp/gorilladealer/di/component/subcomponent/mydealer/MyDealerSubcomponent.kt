package com.technoecorp.gorilladealer.di.component.subcomponent.mydealer

import com.technoecorp.gorilladealer.ui.mydealer.MyDealerActivity
import dagger.Subcomponent

@MyDealerScope
@Subcomponent(modules = [MyDealerViewModelFactoryModule::class])
interface MyDealerSubcomponent {

    fun inject(myDealerActivity: MyDealerActivity)

    @Subcomponent.Factory
    interface Factory {
        fun create(): MyDealerSubcomponent
    }

}