package uk.co.jamiecruwys.apieceofcake.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*
import uk.co.jamiecruwys.apieceofcake.App
import uk.co.jamiecruwys.apieceofcake.R
import uk.co.jamiecruwys.apieceofcake.api.Cake
import javax.inject.Inject

class MainActivity : AppCompatActivity(), MainView, CakeItemView {

    @Inject
    lateinit var presenter: MainPresenter

    private val adapter: CakeAdapter = CakeAdapter(arrayListOf(), this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        App.appComponent.inject(this)
        presenter.attach(this, this)

        cake_list.layoutManager = LinearLayoutManager(this)
        cake_list.adapter = adapter
        cake_list.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))

        main_content_container.setOnRefreshListener { presenter.loadData(isSwipeToRefresh = true) }
    }

    override fun onResume() {
        super.onResume()
        presenter.onResume()
    }

    override fun showLoading() {
        progress_container.isVisible = true
    }

    override fun hideLoading() {
        progress_container.isVisible = false
    }

    override fun showSwipeToRefreshLoading() {
        main_content_container.isRefreshing = true
    }

    override fun hideSwipeToRefreshLoading() {
        main_content_container.isRefreshing = false
    }

    override fun disableSwipeToRefreshGesture() {
        main_content_container.isEnabled = false
    }

    override fun enableSwipeToRefreshGesture() {
        main_content_container.isEnabled = true
    }

    override fun showEmpty() {
        // TODO:
    }

    override fun hideEmpty() {
        // TODO:
    }

    override fun showNetworkError() {
        // TODO:
    }

    override fun hideNetworkError() {
        // TODO:
    }

    override fun showServerError() {
        error_container.isVisible = true
        error_container.retry_button.setOnClickListener { presenter.loadData() }
    }

    override fun hideServerError() {
        error_container.isVisible = false
    }

    override fun showCakes(cakes: List<Cake>) {
        cake_list.isVisible = true
        adapter.setItems(cakes)
    }

    override fun hideCakes() {
        cake_list.isVisible = false
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