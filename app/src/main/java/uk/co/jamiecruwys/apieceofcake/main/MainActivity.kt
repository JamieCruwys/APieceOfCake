package uk.co.jamiecruwys.apieceofcake.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.os.PersistableBundle
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*
import uk.co.jamiecruwys.apieceofcake.App
import uk.co.jamiecruwys.apieceofcake.R
import uk.co.jamiecruwys.apieceofcake.api.Cake
import uk.co.jamiecruwys.apieceofcake.main.list.CakeAdapter
import uk.co.jamiecruwys.apieceofcake.main.list.CakeItemView
import javax.inject.Inject

class MainActivity : AppCompatActivity(), MainView, CakeItemView {

    @Inject
    lateinit var presenter: MainPresenter

    private val adapter: CakeAdapter =
        CakeAdapter(arrayListOf(), this)

    private val layoutManager = LinearLayoutManager(this)

    private var listState: Parcelable? = null
    private var dataState: ArrayList<Cake>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        App.appComponent.inject(this)
        presenter.attach(this, this)

        cake_list.layoutManager = layoutManager
        cake_list.adapter = adapter
        cake_list.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        cake_list.layoutAnimation = AnimationUtils.loadLayoutAnimation(this, R.anim.layout_animation_fall_down)

        main_content_container.setOnRefreshListener { presenter.loadData(isSwipeToRefresh = true) }

        if (savedInstanceState == null) {
            presenter.loadData()
        }
    }

    override fun onResume() {
        super.onResume()
        dataState?.let {
            presenter.restoreState(it)
        }
        listState?.let {
            cake_list?.layoutManager?.onRestoreInstanceState(it)
        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        listState = cake_list.layoutManager?.onSaveInstanceState()
        outState?.putParcelable(RECYCLER_VIEW_STATE_KEY, listState)

        dataState = adapter.getItems()
        outState?.putParcelableArrayList(RECYCLER_VIEW_ITEMS_KEY, dataState)
    }

    override fun onSaveInstanceState(outState: Bundle?, outPersistentState: PersistableBundle?) {
        super.onSaveInstanceState(outState, outPersistentState)
        listState = cake_list.layoutManager?.onSaveInstanceState()
        outState?.putParcelable(RECYCLER_VIEW_STATE_KEY, listState)

        dataState = adapter.getItems()
        outState?.putParcelableArrayList(RECYCLER_VIEW_ITEMS_KEY, dataState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        savedInstanceState?.let {
            listState = it.getParcelable(RECYCLER_VIEW_STATE_KEY)
            dataState = it.getParcelableArrayList(RECYCLER_VIEW_ITEMS_KEY)
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onRestoreInstanceState(savedInstanceState, persistentState)
        savedInstanceState?.let {
            listState = it.getParcelable(RECYCLER_VIEW_STATE_KEY)
            dataState = it.getParcelableArrayList(RECYCLER_VIEW_ITEMS_KEY)
        }
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
        empty_list_container.isVisible = true
        empty_list_retry_button.setOnClickListener { presenter.loadData() }
    }

    override fun hideEmpty() {
        empty_list_container.isVisible = false
    }

    override fun showNetworkError() {
        network_error_container.isVisible = true
        network_error_retry_button.setOnClickListener { presenter.loadData() }
    }

    override fun hideNetworkError() {
        network_error_container.isVisible = false
    }

    override fun showServerError() {
        server_error_container.isVisible = true
        server_error_retry_button.setOnClickListener { presenter.loadData() }
    }

    override fun hideServerError() {
        server_error_container.isVisible = false
    }

    override fun showCakes(cakes: List<Cake>) {
        cake_list.isVisible = true
        adapter.setItems(cakes)
        cake_list.scheduleLayoutAnimation()
    }

    override fun hideCakes() {
        cake_list.isVisible = false
    }

    override fun clearCakes() {
        adapter.setItems(listOf())
        cake_list.scheduleLayoutAnimation()
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

    companion object {
        const val RECYCLER_VIEW_STATE_KEY = "recycler_view_state"
        const val RECYCLER_VIEW_ITEMS_KEY = "recycler_view_items"
    }
}