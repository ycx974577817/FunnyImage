package me.yangcx.funnyimage.application

import android.app.Activity
import android.support.multidex.MultiDexApplication
import android.support.v4.app.Fragment
import com.luliang.shapeutils.DevShapeUtils
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import me.yangcx.funnyimage.di.component.*
import me.yangcx.funnyimage.di.module.ApplicationModule
import me.yangcx.funnyimage.log.FileTree
import timber.log.Timber
import javax.inject.Inject

class FunnyApplication : MultiDexApplication(), HasActivityInjector {
    @Inject
    lateinit var activityInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()
        initComponents()
        initTimer()
        initDevShape()
    }

    private fun initComponents() {
        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(ApplicationModule(this))
                .build()
        DaggerGlobalComponent
                .builder()
                .build()
                .inject(this)
    }

    private fun initTimer() {
        Timber.plant(FileTree(true))
    }

    private fun initDevShape() {
        DevShapeUtils.init(this)
    }

    override fun activityInjector() = activityInjector


    companion object {
        lateinit var applicationComponent: ApplicationComponent
        val gsonComponent: GsonComponent by lazy {
            DaggerGsonComponent.builder()
                    .build()
        }
        val directoryComponent: DirectoryComponent by lazy {
            DaggerDirectoryComponent.builder()
                    .applicationComponent(applicationComponent)
                    .build()
        }
        val repositoryComponent: RepositoryComponent by lazy {
            DaggerRepositoryComponent.builder()
                    .applicationComponent(applicationComponent)
                    .gsonComponent(gsonComponent)
                    .directoryComponent(directoryComponent)
                    .build()
        }

        fun get(activity: Activity) = activity.application as FunnyApplication
        fun get(fragment: Fragment) = get(fragment.requireActivity())
    }
}