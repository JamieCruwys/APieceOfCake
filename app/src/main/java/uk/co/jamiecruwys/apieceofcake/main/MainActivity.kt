package uk.co.jamiecruwys.apieceofcake.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import uk.co.jamiecruwys.apieceofcake.App
import uk.co.jamiecruwys.apieceofcake.R
import uk.co.jamiecruwys.apieceofcake.api.Cake

class MainActivity : AppCompatActivity(), MainView, CakeItemView {

    private lateinit var presenter: MainPresenter
    private val adapter: CakeAdapter = CakeAdapter(arrayListOf(), this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        App.appComponent.inject(this)
        presenter = MainPresenter(this, this)

        cake_list.layoutManager = LinearLayoutManager(this)
        cake_list.adapter = adapter
        cake_list.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))

        main_content_container.setOnRefreshListener { presenter.loadData() }
    }

    override fun onResume() {
        super.onResume()
        presenter.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }

    override fun showLoading() {
        progress_container.isVisible = true
        showCakes(listOf())
    }

    override fun hideLoading() {
        progress_container.isVisible = false
        main_content_container.isRefreshing = false
    }

    override fun showError() {
        error_container.isVisible = true
    }

    override fun hideError() {
        error_container.isVisible = false
    }

    override fun showCakes(cakes: List<Cake>) {
        adapter.setItems(cakes)
    }

    override fun clearCakes() {
        adapter.setItems(listOf())
    }

    override fun onCakeClicked(cake: Cake) {
        presenter.onCakeItemClicked(cake)
    }

    override fun showCakeInfoDialog(cake: Cake) {
        AlertDialog.Builder(this)
            .setTitle(cake.title)
            .setMessage(cake.desc)
            .setCancelable(true)
            .setPositiveButton(android.R.string.ok) { dialog, _ -> dialog.dismiss() }
            .create()
            .show()
    }
}