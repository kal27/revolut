package com.example.myapplication.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.model.Rate
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity(), MainActivityPresenter.View {

    @Inject
    lateinit var presenter: MainActivityPresenter

    private val adapter: RatesAdapter = RatesAdapter(
        onRateChanged = { first, second -> presenter.onRateChanged(first, second) })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        presenter.onAttach(this)

        activityMain_ratesList.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = this@MainActivity.adapter
        }
    }

    override fun loadRates(list: List<Rate>) {
        adapter.setData(list)
    }

    override fun updateBaseValue(baseValue: Double, base: String) {
        adapter.updateBaseValue(baseValue, base)
    }

    override fun presentToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun showProgressBar() {
        activityMain_progressBar.visibility = View.VISIBLE
    }

    override fun hideProgressBar() {
        activityMain_progressBar.visibility = View.GONE
    }

    override fun onDestroy() {
        presenter.onDetach()

        super.onDestroy()
    }
}
