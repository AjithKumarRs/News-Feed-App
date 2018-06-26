package com.voltek.newsfeed.presentation.ui.splash

import com.arellomobile.mvp.InjectViewState
import com.voltek.newsfeed.domain.exception.NoNewsSourcesSelectedException
import com.voltek.newsfeed.domain.usecase.Parameter
import com.voltek.newsfeed.domain.usecase.newssources.NewsSourcesUseCase
import com.voltek.newsfeed.presentation.base.BasePresenterOld
import com.voltek.newsfeed.presentation.base.Event
import com.voltek.newsfeed.presentation.entity.SourceUI
import com.voltek.newsfeed.presentation.navigation.command.CommandOpenArticlesListScreen
import com.voltek.newsfeed.presentation.navigation.command.CommandOpenNewsSourcesScreen
import com.voltek.newsfeed.presentation.navigation.command.CommandSystemMessage
import com.voltek.newsfeed.presentation.navigation.proxy.RouterOld
import io.reactivex.functions.Action
import io.reactivex.functions.Consumer

@InjectViewState
class SplashPresenter(
        private val router: RouterOld,
        private val newsSources: NewsSourcesUseCase
) : BasePresenterOld<SplashView>() {

    override fun event(event: Event) {}

    init {
        bind(arrayOf(newsSources))
    }

    override fun onFirstViewAttach() {
        checkNewsSources()
    }

    private fun checkNewsSources() {
        // Fetch news sources in background.
        // Check, if there is no enabled news sources, open NewsSources screen with proper message.
        newsSources.execute(
                Parameter(NewsSourcesUseCase.GET),
                Consumer {
                    result(hasEnabled(it?.data ?: ArrayList()))
                },
                Consumer {
                    it.printStackTrace()
                    result(false)
                },
                Action {}
        )
    }

    private fun hasEnabled(sources: List<SourceUI>): Boolean = sources.any { it.isEnabled }

    private fun result(hasEnabled: Boolean) {
        router.execute(CommandOpenArticlesListScreen())

        if (!hasEnabled) {
            router.execute(CommandOpenNewsSourcesScreen())
            router.execute(CommandSystemMessage(error = NoNewsSourcesSelectedException()))
        }
    }
}
