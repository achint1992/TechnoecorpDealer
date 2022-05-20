package com.technoecorp.gorilladealer.di

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

interface Injector {
    fun getLoginSubComponent(): LoginSubComponent

    fun getRegisterSubComponent(): RegisterSubComponent

    fun getOtpSubComponent(): OtpSubComponent

    fun getMainSubComponent(): MainSubcomponent

    fun getDashboardSubComponent(): DashboardSubcomponent

    fun getMyDealerSubComponent(): MyDealerSubcomponent

    fun getProfileSubComponent(): ProfileSubComponent

    fun getKycSubComponent(): KycSubcomponent

    fun getProductSubComponent(): ProductSubcomponent

    fun getGallerySubComponent(): GallerySubcomponent

}