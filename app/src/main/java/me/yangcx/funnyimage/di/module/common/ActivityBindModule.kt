package me.yangcx.funnyimage.di.module.common

import dagger.Module
import dagger.android.ContributesAndroidInjector
import me.yangcx.funnyimage.di.scope.ActivityScope
import me.yangcx.funnyimage.ui.main.HomeActivity
import me.yangcx.funnyimage.ui.splash.SplashActivity

@Module
abstract class ActivityBindModule {
    @ActivityScope
    @ContributesAndroidInjector
    abstract fun splashInjector(): SplashActivity

    @ActivityScope
    @ContributesAndroidInjector(modules = [AdapterModule::class])
    abstract fun homeInjector(): HomeActivity
}