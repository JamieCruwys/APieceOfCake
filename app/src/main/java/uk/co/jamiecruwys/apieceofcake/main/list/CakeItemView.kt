package uk.co.jamiecruwys.apieceofcake.main.list

import uk.co.jamiecruwys.apieceofcake.api.Cake

interface CakeItemView {

    fun onCakeClicked(cake: Cake)

    fun showCakeInfoDialog(cake: Cake)
}