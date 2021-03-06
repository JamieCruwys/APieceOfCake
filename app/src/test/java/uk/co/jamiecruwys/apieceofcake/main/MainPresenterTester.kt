package uk.co.jamiecruwys.apieceofcake.main

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doAnswer
import com.nhaarman.mockitokotlin2.eq
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import uk.co.jamiecruwys.apieceofcake.api.Cake
import uk.co.jamiecruwys.apieceofcake.api.CakeRequest
import uk.co.jamiecruwys.apieceofcake.main.list.CakeItemView

class MainPresenterTester {

    private var mainView: MainView = mock()
    private var cakeView: CakeItemView = mock()
    private var cakeRequest: CakeRequest = mock()
    private var presenter: MainPresenter = MainPresenter(cakeRequest)

    init {
        presenter.attach(mainView, cakeView)
    }

    fun loadData(isSwipeToRefresh: Boolean) {
        presenter.loadData(isSwipeToRefresh)
    }

    fun verifySuccess(isSwipeToRefresh: Boolean, cakes: List<Cake>) {
        verifyLoadDataCalled(isSwipeToRefresh, true)
        verify(mainView).showCakes(cakes)
        verify(mainView).enableSwipeToRefreshGesture()
        verifyCompletion(isSwipeToRefresh)
    }

    fun verifyEmpty(isSwipeToRefresh: Boolean) {
        verifyLoadDataCalled(isSwipeToRefresh, false)
        verify(mainView).showEmpty()
        verifyCompletion(isSwipeToRefresh)
    }

    fun verifyServerError(isSwipeToRefresh: Boolean) {
        verifyLoadDataCalled(isSwipeToRefresh, false)
        verify(mainView).showServerError()
        verifyCompletion(isSwipeToRefresh)
    }

    fun verifyNetworkError(isSwipeToRefresh: Boolean) {
        verifyLoadDataCalled(isSwipeToRefresh, false)
        verify(mainView).showNetworkError()
        verifyCompletion(isSwipeToRefresh)
    }

    private fun verifyLoadDataCalled(isSwipeToRefresh: Boolean, shouldAllowSwipeToRefreshAfter: Boolean) {
        if (isSwipeToRefresh) {
            verify(mainView).hideLoading()
        } else {
            verify(mainView, times(2)).hideLoading()
        }

        verify(mainView).hideServerError()
        verify(mainView).hideNetworkError()
        verify(mainView).hideEmpty()
        verify(mainView).clearCakes()
        verify(mainView).hideCakes()

        if (!isSwipeToRefresh) {
            verify(mainView).hideSwipeToRefreshLoading()
            if (shouldAllowSwipeToRefreshAfter) {
                verify(mainView).disableSwipeToRefreshGesture()
            } else {
                verify(mainView, times(2)).disableSwipeToRefreshGesture()
            }
            verify(mainView).showLoading()
        } else if (!shouldAllowSwipeToRefreshAfter) {
            verify(mainView).disableSwipeToRefreshGesture()
        }
    }

    private fun verifyCompletion(isSwipeToRefresh: Boolean) {
        if (isSwipeToRefresh) {
            verify(mainView).hideSwipeToRefreshLoading()
        }
    }

    fun setSuccessResponse(cakes: List<Cake?>? = listOf()) {
        doAnswer {
            val listener = it.arguments[0] as CakeRequest.Listener
            listener.onSuccess.invoke(cakes)
            listener.onCompletion.invoke()
            null
        }.whenever(cakeRequest).execute(any())
    }

    fun setServerErrorResponse(responseCode: Int = 404) {
        doAnswer {
            val listener = it.arguments[0] as CakeRequest.Listener
            listener.onServerError.invoke(responseCode)
            listener.onCompletion.invoke()
            null
        }.whenever(cakeRequest).execute(any())
    }

    fun setNetworkErrorResponse() {
        doAnswer {
            val listener = it.arguments[0] as CakeRequest.Listener
            listener.onNetworkError.invoke()
            listener.onCompletion.invoke()
            null
        }.whenever(cakeRequest).execute(any())
    }

    fun clickCakeItem(cake: Cake) {
        presenter.onCakeItemClicked(cake)
    }

    fun verifyCakeItemClicked(cake: Cake) {
        verify(cakeView).showCakeInfoDialog(eq(cake))
    }
}