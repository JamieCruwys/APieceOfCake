package uk.co.jamiecruwys.apieceofcake.main

interface MainView {

    fun showLoading()

    fun hideLoading()

    fun showError()

    fun hideError()

    fun showResponse(response: String)

    fun hideResponse()
}