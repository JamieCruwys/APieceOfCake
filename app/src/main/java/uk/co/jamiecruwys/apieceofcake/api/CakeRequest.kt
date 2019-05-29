package uk.co.jamiecruwys.apieceofcake.api

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class CakeRequest @Inject constructor(private val apiService: ApiService) {

    data class Listener(
        val onSuccess: (cakes: List<Cake?>?) -> Unit,
        val onServerError: (httpResponseCode: Int) -> Unit,
        val onNetworkError: () -> Unit,
        val onCompletion: () -> Unit
    )

    fun execute(listener: Listener) {
        apiService.getCakeList().enqueue(object : Callback<List<Cake?>?> {
            override fun onFailure(call: Call<List<Cake?>?>, t: Throwable) {
                listener.onNetworkError()
                listener.onCompletion()
            }

            override fun onResponse(call: Call<List<Cake?>?>, response: Response<List<Cake?>?>) {
                if (response.isSuccessful) {
                    listener.onSuccess(response.body())
                } else {
                    listener.onServerError(response.code())
                }
                listener.onCompletion()
            }
        })
    }
}