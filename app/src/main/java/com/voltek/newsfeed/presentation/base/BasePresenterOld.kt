package com.voltek.newsfeed.presentation.base

import com.arellomobile.mvp.MvpPresenter
import com.voltek.newsfeed.domain.usecase.BaseUseCase

abstract class BasePresenterOld<View : BaseViewOld> : MvpPresenter<View>() {

    private val useCases = ArrayList<BaseUseCase<*, *>>()

    abstract fun event(event: Event)

    override fun attachView(view: View?) {
        super.attachView(view)
        viewState.attachInputListeners()
    }

    override fun detachView(view: View?) {
        viewState.detachInputListeners()
        super.detachView(view)
    }

    override fun onDestroy() {
        for (useCase in useCases)
            useCase.unsubscribe()
    }

    protected fun bind(useCases: Array<BaseUseCase<*, *>>) =
            this.useCases.addAll(useCases)
}
