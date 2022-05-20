package com.technoecorp.gorilladealer.ui

import android.app.Application
import com.technoecorp.data.di.NetModule
import com.technoecorp.domain.domainmodel.data.Dealer
import com.technoecorp.gorilladealer.BuildConfig
import com.technoecorp.gorilladealer.di.AppModule
import com.technoecorp.gorilladealer.di.Injector
import com.technoecorp.gorilladealer.di.component.AppComponent
import com.technoecorp.gorilladealer.di.component.DaggerAppComponent
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
import com.technoecorp.gorilladealer.utils.Constants
import timber.log.Timber

class TechnoecorpApplication : Application(), Injector {
    private lateinit var appComponent: AppComponent
    private var dealer: Dealer? = null

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
            .netModule(NetModule(Constants.appUrl))
            .appModule(AppModule(applicationContext)).build()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

    }

    override fun getLoginSubComponent(): LoginSubComponent {
        return appComponent.getLoginSubComponent().create()
    }

    override fun getRegisterSubComponent(): RegisterSubComponent {
        return appComponent.getRegisterSubComponent().create()
    }

    override fun getOtpSubComponent(): OtpSubComponent {
        return appComponent.getOtpSubComponent().create()
    }

    override fun getMainSubComponent(): MainSubcomponent {
        return appComponent.getMainSubComponent().create()
    }

    override fun getDashboardSubComponent(): DashboardSubcomponent {
        return appComponent.getDashboardSubComponent().create()
    }

    override fun getMyDealerSubComponent(): MyDealerSubcomponent {
        return appComponent.getMyDealerSubComponent().create()
    }

    override fun getProfileSubComponent(): ProfileSubComponent {
        return appComponent.getProfileSubComponent().create()
    }

    override fun getKycSubComponent(): KycSubcomponent {
        return appComponent.getKycSubComponent().create()
    }

    override fun getProductSubComponent(): ProductSubcomponent {
        return appComponent.getProductSubComponent().create()
    }

    override fun getGallerySubComponent(): GallerySubcomponent {
        return appComponent.getGallerySubComponent().create()
    }


    fun updateDealer(updatedDealer: Dealer?) {
        this.dealer = updatedDealer
    }

    fun getUpdateDealer(): Dealer? {
        return this.dealer
    }

}