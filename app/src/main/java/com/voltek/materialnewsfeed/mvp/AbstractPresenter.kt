package com.voltek.materialnewsfeed.mvp

abstract class AbstractPresenter<out Nav : BaseNavigator, View : BaseView>(nav: Nav) : BasePresenter<View> {

    protected var mView: View? = null

    protected val mNavigator: Nav = nav

    override fun onAttach(view: View) {
        mView = view
    }

    override fun onDetach() {
        mView = null
    }
}