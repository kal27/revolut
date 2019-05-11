package com.example.myapplication.ui

import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity(), MainActivityPresenter.View {

    @Inject
    lateinit var presenter: MainActivityPresenter

    private val adapter: RatesAdapter = RatesAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        presenter.onAttach(this)

        activityMain_ratesList.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = this@MainActivity.adapter
        }
    }

    override fun loadRates(list: List<Pair<String, Double>>) {
        adapter.setData(list)
    }

    override fun presentToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        presenter.onDetach()

        super.onDestroy()
    }
}
