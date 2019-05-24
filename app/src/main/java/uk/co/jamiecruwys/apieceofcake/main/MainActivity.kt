package uk.co.jamiecruwys.apieceofcake.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import uk.co.jamiecruwys.apieceofcake.App
import uk.co.jamiecruwys.apieceofcake.R
import uk.co.jamiecruwys.apieceofcake.api.Cake

class MainActivity : AppCompatActivity(), MainView {

    private lateinit var presenter: MainPresenter
    private val adapter: CakeAdapter = CakeAdapter(arrayListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        App.appComponent.inject(this)
        presenter = MainPresenter(this)

        cake_list.layoutManager = LinearLayoutManager(this)
        cake_list.adapter = adapter
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
    }

    override fun hideLoading() {
        progress_container.isVisible = false
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
}