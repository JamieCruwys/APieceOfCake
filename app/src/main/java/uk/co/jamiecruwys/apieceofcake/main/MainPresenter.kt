package uk.co.jamiecruwys.apieceofcake.main

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import uk.co.jamiecruwys.apieceofcake.App
import uk.co.jamiecruwys.apieceofcake.api.ApiService
import uk.co.jamiecruwys.apieceofcake.api.Cake
import javax.inject.Inject

class MainPresenter(private val view: MainView?, private val cakeView: CakeItemView) {

    @Inject
    lateinit var apiService: ApiService

    init {
        App.appComponent.inject(this)
    }

    fun onResume() {
        view?.hideError()
        view?.showLoading()
        apiService.getCakeList().enqueue(object : Callback<List<Cake>> {
            override fun onResponse(call: Call<List<Cake>>, response: Response<List<Cake>>) {
                view?.hideLoading()

                val cakes = filterCakes(response.body())

                if (cakes.isEmpty()) {
                    view?.showError()
                } else {
                    view?.showCakes(cakes)
                }
            }

            override fun onFailure(call: Call<List<Cake>>, t: Throwable) {
                view?.hideLoading()
                view?.showError()
            }
        })
    }

    fun filterCakes(cakes: List<Cake?>?): List<Cake> {
        cakes ?: return listOf()
        val availableCakes: List<Cake> = cakes.filterNotNull()
        val uniqueCakes: List<Cake> = availableCakes.distinctBy { it.title }
        val sortedCakes = uniqueCakes.sortedBy { it.title }

        val capitalisedTextCakes = arrayListOf<Cake>()
        sortedCakes.forEach { cake ->
            capitalisedTextCakes.add(Cake(
                cake.title?.capitalize() ?: "",
                cake.desc?.capitalize() ?: "",
                cake.image
            ))
        }

        return capitalisedTextCakes
    }

    fun onCakeItemClicked(cake: Cake) {
        cakeView.showCakeInfoDialog(cake)
    }

    fun onDestroy() {}
}