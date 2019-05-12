package com.example.myapplication.ui

import com.example.myapplication.model.Conversion
import com.example.myapplication.model.Rate
import com.example.myapplication.network.ConversionsService
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class MainActivityPresenter @Inject constructor(private val service: ConversionsService) {

    private var disposable: Disposable? = null
        set(value) {
            field?.dispose()
            field = value
        }

    private var view: View? = null

    private var base = "EUR"

    fun onAttach(view: View) {
        this.view = view

        startUpdating()
    }

    private fun startUpdating() {
        showLoadingIndication()

        disposable = Observable
            .interval(1, TimeUnit.SECONDS)
            .flatMap { updateData() }
            .subscribe(this::handleResult, this::handleError)
    }

    private fun showLoadingIndication() {
        view?.hideList()
        view?.showProgressBar()
    }

    private fun hideLoadingIndication() {
        view?.hideProgressBar()
        view?.showList()
    }

    private fun handleResult(conversion: Conversion) {
        val list = conversion.rates.toList()
        view?.loadRates(list.map { Rate(it.first, it.second) })
        hideLoadingIndication()
    }

    fun onRefresh() {
        startUpdating()
    }

    private fun handleError(error: Throwable) {
        view?.presentToast(error.localizedMessage)
    }

    private fun updateData(): Observable<Conversion> {
        return service.conversions(base)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.single())
    }

    fun onRateChanged(base: String, value: String) {
        this.base = base
        val baseValue = value.toDoubleOrNull() ?: return
        view?.updateBaseValue(baseValue, base)
    }

    fun onDetach() {
        disposable = null
        view = null
    }

    interface View {
        fun hideList()
        fun showList()
        fun showProgressBar()
        fun hideProgressBar()
        fun loadRates(list: List<Rate>)
        fun updateBaseValue(baseValue: Double, base: String)
        fun presentToast(message: String)
    }
}
