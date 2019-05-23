package uk.co.jamiecruwys.apieceofcake.main

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import uk.co.jamiecruwys.apieceofcake.App
import uk.co.jamiecruwys.apieceofcake.api.ApiService
import uk.co.jamiecruwys.apieceofcake.api.Cake
import javax.inject.Inject

class MainPresenter(private val view: MainView?) {

    @Inject
    lateinit var apiService: ApiService

    init {
        App.appComponent.inject(this)
    }

    fun onResume() {
        view?.hideError()
        view?.hideResponse()
        view?.showLoading()
        apiService.getCakeList().enqueue(object: Callback<List<Cake>> {
            override fun onResponse(call: Call<List<Cake>>, response: Response<List<Cake>>) {
                view?.hideLoading()
                val dto = response.body()
                dto?.let {
                    view?.showResponse(it.toString())
                } ?: view?.showError()
            }

            override fun onFailure(call: Call<List<Cake>>, t: Throwable) {
                view?.hideLoading()
                view?.showError()
            }
        })
    }

    fun onDestroy() {
    }
}