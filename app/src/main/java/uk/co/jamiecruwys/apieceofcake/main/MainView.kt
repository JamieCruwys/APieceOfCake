package uk.co.jamiecruwys.apieceofcake.main

import uk.co.jamiecruwys.apieceofcake.api.Cake

interface MainView {

    fun showLoading()

    fun hideLoading()

    fun showSwipeToRefreshLoading()

    fun hideSwipeToRefreshLoading()

    fun disableSwipeToRefreshGesture()

    fun enableSwipeToRefreshGesture()

    fun showEmpty()

    fun hideEmpty()

    fun showServerError()

    fun hideServerError()

    fun showNetworkError()

    fun hideNetworkError()

    fun showCakes(cakes: List<Cake>)

    fun hideCakes()

    fun clearCakes()

}