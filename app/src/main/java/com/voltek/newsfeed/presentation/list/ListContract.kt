package com.voltek.newsfeed.presentation.list

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.voltek.newsfeed.data.entity.ArticleRAW
import com.voltek.newsfeed.presentation.BaseView

object ListContract {

    class ListModel {
        var loading: Boolean = false
        var articles: ArrayList<ArticleRAW> = ArrayList()
        var message: String = ""

        fun addData(data: List<ArticleRAW>?) {
            if (data != null && !data.isEmpty())
                articles.addAll(data)
        }
    }

    interface ListView : BaseView {

        @StateStrategyType(AddToEndSingleStrategy::class)
        fun render(model: ListModel)
    }
}
