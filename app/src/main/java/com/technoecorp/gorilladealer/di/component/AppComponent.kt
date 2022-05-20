package com.technoecorp.gorilladealer.di.component

import com.technoecorp.data.di.DataSourceModule
import com.technoecorp.data.di.NetModule
import com.technoecorp.data.di.RepositoryModule
import com.technoecorp.domain.di.AuthUseCaseModule
import com.technoecorp.domain.di.CompanyUseCaseModule
import com.technoecorp.domain.di.DealerUseCaseModule
import com.technoecorp.gorilladealer.di.AppModule
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
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        NetModule::class,
        DataSourceModule::class,
        RepositoryModule::class,
        AuthUseCaseModule::class,
        DealerUseCaseModule::class,
        CompanyUseCaseModule::class
    ]
)
interface AppComponent {
    fun getLoginSubComponent(): LoginSubComponent.Factory
    fun getRegisterSubComponent(): RegisterSubComponent.Factory
    fun getOtpSubComponent(): OtpSubComponent.Factory
    fun getMainSubComponent(): MainSubcomponent.Factory
    fun getDashboardSubComponent(): DashboardSubcomponent.Factory
    fun getMyDealerSubComponent(): MyDealerSubcomponent.Factory
    fun getProfileSubComponent(): ProfileSubComponent.Factory
    fun getKycSubComponent(): KycSubcomponent.Factory
    fun getGallerySubComponent(): GallerySubcomponent.Factory
    fun getProductSubComponent(): ProductSubcomponent.Factory
}