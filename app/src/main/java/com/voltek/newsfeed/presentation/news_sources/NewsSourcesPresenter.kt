package com.voltek.newsfeed.presentation.news_sources

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.voltek.newsfeed.NewsApp
import com.voltek.newsfeed.domain.interactor.Parameter
import com.voltek.newsfeed.domain.interactor.news_sources.NewsSourcesInteractor
import com.voltek.newsfeed.presentation.Event
import com.voltek.newsfeed.presentation.news_sources.NewsSourcesContract.NewsSourcesModel
import com.voltek.newsfeed.presentation.news_sources.NewsSourcesContract.NewsSourcesView
import io.reactivex.functions.Action
import io.reactivex.functions.Consumer
import javax.inject.Inject

@InjectViewState
class NewsSourcesPresenter : MvpPresenter<NewsSourcesView>() {

    @Inject
    lateinit var mNewsSources: NewsSourcesInteractor

    private var mModel: NewsSourcesModel = NewsSourcesModel()

    fun notify(event: Event) {
        when (event) {
            is Event.FilterSources -> {
                mModel.categoryId = event.id
                loadNewsSources(event.filter)
            }
            is Event.Refresh -> {
                mModel.resetId()
                loadNewsSources(NewsSourcesInteractor.REFRESH)
            }
            is Event.EnableNewsSource -> {
                mNewsSources.execute(
                        Parameter(NewsSourcesInteractor.ENABLE, event.source),
                        Consumer {}, Consumer {}, Action {}
                )

                mModel.sources.firstOrNull {
                    it.id == event.source.id
                }?.isEnabled = !event.source.isEnabled
            }
        }
    }

    private fun updateModel() {
        viewState.render(mModel)
    }

    init {
        NewsApp.presenterComponent.inject(this)
        loadNewsSources(NewsSourcesInteractor.GET)
    }

    override fun attachView(view: NewsSourcesView?) {
        super.attachView(view)
        viewState.attachInputListeners()
    }

    override fun detachView(view: NewsSourcesView?) {
        viewState.detachInputListeners()
        super.detachView(view)
    }

    override fun onDestroy() {
        mNewsSources.unsubscribe()
    }

    private fun loadNewsSources(filter: String) {
        mModel.sources.clear()
        mModel.loading = true
        mModel.message = ""
        updateModel()

        mNewsSources.execute(
                Parameter(filter),
                Consumer {
                    mModel.sources = ArrayList(it.data ?: ArrayList())
                    mModel.message = it.message
                    updateModel()
                },
                Consumer {
                    mModel.message = it.message ?: ""
                    finishLoading()
                },
                Action {
                    finishLoading()
                }
        )
    }

    private fun finishLoading() {
        mModel.loading = false
        mNewsSources.unsubscribe()
        updateModel()
    }
}
