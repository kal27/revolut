package com.example.myapplication.ui

import com.example.myapplication.network.ConversionsService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MainActivityPresenter @Inject constructor(private val service: ConversionsService) {

    var disposable: Disposable? = null

    var view: View? = null

    fun onAttach(view: View) {
        this.view = view
        search()
    }

    private fun search() {
        disposable = service.conversions("EUR")
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.single())
            .subscribe({
                view?.loadRates(it.rates.toList())
            }, {
                view?.presentToast(it.localizedMessage)
            })
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
