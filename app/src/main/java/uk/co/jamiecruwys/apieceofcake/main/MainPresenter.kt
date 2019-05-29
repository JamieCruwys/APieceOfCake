package uk.co.jamiecruwys.apieceofcake.main

import uk.co.jamiecruwys.apieceofcake.api.Cake
import uk.co.jamiecruwys.apieceofcake.api.CakeRequest
import uk.co.jamiecruwys.apieceofcake.main.list.CakeItemView
import javax.inject.Inject

class MainPresenter @Inject constructor(private val cakeRequest: CakeRequest) {

    private var view: MainView? = null
    private var cakeView: CakeItemView? = null

    fun attach(view: MainView, cakeView: CakeItemView) {
        this.view = view
        this.cakeView = cakeView
    }

    fun loadData(isSwipeToRefresh: Boolean = false) {
        view?.hideLoading()
        view?.hideServerError()
        view?.hideNetworkError()
        view?.hideEmpty()
        view?.clearCakes()
        view?.hideCakes()

        if (!isSwipeToRefresh) {
            view?.hideSwipeToRefreshLoading()
            view?.disableSwipeToRefreshGesture()
            view?.showLoading()
        }

        cakeRequest.execute(CakeRequest.Listener(
            onSuccess = {
                onSuccess(it)
            },
            onServerError = {
                view?.showServerError()
                view?.disableSwipeToRefreshGesture()
            },
            onNetworkError = {
                view?.showNetworkError()
                view?.disableSwipeToRefreshGesture()
            },
            onCompletion = {
                onCompletion(isSwipeToRefresh)
            }
        ))
    }

    private fun onSuccess(cakes: List<Cake?>?) {
        val items = filterCakes(cakes)
        if (items.isEmpty()) {
            view?.showEmpty()
            view?.disableSwipeToRefreshGesture()
        } else {
            view?.showCakes(items)
            view?.enableSwipeToRefreshGesture()
        }
    }

    private fun onCompletion(isSwipeToRefresh: Boolean) {
        if (isSwipeToRefresh) {
            view?.hideSwipeToRefreshLoading()
        } else {
            view?.hideLoading()
        }
    }

    private fun filterCakes(cakes: List<Cake?>?): List<Cake> {
        val availableCakes = cakes?.filterNotNull() ?: listOf()
        val uniqueCakes = availableCakes.distinctBy { it.title }
        val sortedCakes = uniqueCakes.sortedBy { it.title }
        return capitaliseCakeText(sortedCakes)
    }

    private fun capitaliseCakeText(cakes: List<Cake>): List<Cake> {
        val capitalisedTextCakes = arrayListOf<Cake>()
        cakes.forEach { cake ->
            capitalisedTextCakes.add(Cake(
                cake.title?.capitalize() ?: "",
                cake.desc?.capitalize() ?: "",
                cake.image
            ))
        }
        return capitalisedTextCakes
    }

    fun restoreState(cakes: List<Cake>) {
        view?.hideLoading()
        view?.hideServerError()
        view?.hideNetworkError()
        view?.hideEmpty()
        view?.clearCakes()
        view?.hideCakes()

        view?.hideSwipeToRefreshLoading()
        view?.disableSwipeToRefreshGesture()

        onSuccess(cakes)
        onCompletion(false)
    }

    fun onCakeItemClicked(cake: Cake) {
        cakeView?.showCakeInfoDialog(cake)
    }
}