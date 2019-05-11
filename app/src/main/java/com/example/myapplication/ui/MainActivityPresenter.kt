package com.example.myapplication.ui

import com.example.myapplication.model.Conversion
import com.example.myapplication.network.ConversionsService
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class MainActivityPresenter @Inject constructor(private val service: ConversionsService) {

    var disposable: Disposable? = null

    var view: View? = null

    private var hasFocus = false

    fun onAttach(view: View) {
        this.view = view
        startUpdating()
    }

    private fun startUpdating() {
        disposable = Observable
            .interval(1, TimeUnit.SECONDS)
            .filter { !hasFocus }
            .flatMap { updateData() }
            .subscribe({
                view?.loadRates(it.rates.toList())
            }, {
                view?.presentToast(it.localizedMessage)
            })
    }

    fun onFocusChanged(hasFocus: Boolean) {
        this.hasFocus = hasFocus
    }

    private fun updateData(): Observable<Conversion> {
        return service.conversions("EUR")
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.single())
    }

    fun onListClicked() {
        hasFocus = false
    }

    fun onDetach() {
        disposable?.dispose()
        view = null
    }

    interface View {
        fun loadRates(list: List<Pair<String, Double>>)
        fun presentToast(message: String)
    }
}
