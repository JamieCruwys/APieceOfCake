package uk.co.jamiecruwys.apieceofcake.main

import uk.co.jamiecruwys.apieceofcake.api.Cake

interface CakeItemView {

    fun onCakeClicked(cake: Cake)

    fun showCakeInfoDialog(cake: Cake)
}