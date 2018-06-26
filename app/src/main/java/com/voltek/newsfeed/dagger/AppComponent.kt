package com.voltek.newsfeed.dagger

import com.voltek.newsfeed.features.entrypoint.EntryPointActivity
import com.voltek.newsfeed.presentation.ui.details.DetailsFragment
import com.voltek.newsfeed.presentation.ui.list.ListFragment
import com.voltek.newsfeed.presentation.ui.newssources.NewsSourcesFragment
import com.voltek.newsfeed.presentation.ui.splash.SplashActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AppModule::class,
    NetworkModule::class,
    PlatformModule::class,
    PresenterModule::class,
    RepositoryModule::class,
    RouterModule::class,
    RouterModule::class,
    StorageModule::class,
    UseCaseModule::class,
    AnalyticsModule::class,
    NavigationModule::class
])
interface AppComponent {

    fun inject(activity: SplashActivity)

    fun inject(fragment: NewsSourcesFragment)
    fun inject(fragment: ListFragment)
    fun inject(fragment: DetailsFragment)

    /* ------------------------------------------------- */

    fun inject(entryPointActivity: EntryPointActivity)
}
