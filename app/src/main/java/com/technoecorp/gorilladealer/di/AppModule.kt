package com.technoecorp.gorilladealer.di

import android.content.Context
import com.technoecorp.gorilladealer.di.component.subcomponent.dashboard.DashboardSubcomponent
import com.technoecorp.gorilladealer.di.component.subcomponent.gallery.GallerySubcomponent
import com.technoecorp.gorilladealer.di.component.subcomponent.kyc.KycSubcomponent
import com.technoecorp.gorilladealer.di.component.subcomponent.login.LoginSubComponent
import com.technoecorp.gorilladealer.di.component.subcomponent.main.MainSubcomponent
import com.technoecorp.gorilladealer.di.component.subcomponent.mydealer.MyDealerSubcomponent
import com.technoecorp.gorilladealer.di.component.subcomponent.otp.OtpSubComponent
import com.technoecorp.gorilladealer.di.component.subcomponent.product.ProductSubcomponent
import com.technoecorp.gorilladealer.di.component.subcomponent.profile.ProfileSubComponent
import com.technoecorp.gorilladealer.di.component.subcomponent.register.RegisterSubComponent
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(
    subcomponents = [
        LoginSubComponent::class,
        RegisterSubComponent::class,
        OtpSubComponent::class,
        MainSubcomponent::class,
        DashboardSubcomponent::class,
        MyDealerSubcomponent::class,
        ProfileSubComponent::class,
        KycSubcomponent::class,
        GallerySubcomponent::class,
        ProductSubcomponent::class
    ]
)
class AppModule(private var context: Context) {

    @Singleton
    @Provides
    fun provideApplicationContext(): Context {
        return context.applicationContext
    }
}