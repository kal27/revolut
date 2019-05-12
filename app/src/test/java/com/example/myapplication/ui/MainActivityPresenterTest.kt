package com.example.myapplication.ui

import com.example.myapplication.model.Conversion
import com.example.myapplication.model.Rate
import com.example.myapplication.network.ConversionsService
import com.nhaarman.mockitokotlin2.*
import io.reactivex.Observable
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Test


class MainActivityPresenterTest {
    private var service: ConversionsService = mock()
    private val view: MainActivityPresenter.View = mock()
    private var presenter: MainActivityPresenter = MainActivityPresenter(service)
    private val defaultBase = "EUR"

    @Before
    fun setUp() {
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { _ -> Schedulers.trampoline() }
    }

    @Test
    fun `progress bar is shown on attach`() {
        presenter.onAttach(view)

        verify(view).showProgressBar()
        verify(view).hideList()
    }

    @Test
    fun `view displays toast when service error occurs`() {
        val message = "localized"
        val error: Throwable = mock { whenever(it.localizedMessage) doReturn message }
        service = mock { whenever(it.conversions(defaultBase)) doReturn Observable.error(error) }
        presenter = MainActivityPresenter(service)
        presenter.onAttach(view)

        Thread.sleep(1100)

        verify(view).presentToast(message)
    }

    @Test
    fun `view displays list and hides progress bar when data is returned by the service`() {
        val map = sortedMapOf(Pair(defaultBase, 2.0))
        val conversion: Conversion = mock {
            whenever(it.rates) doReturn map
        }
        service = mock { whenever(it.conversions(defaultBase)) doReturn Observable.just(conversion) }
        presenter = MainActivityPresenter(service)
        presenter.onAttach(view)

        Thread.sleep(1100)

        verify(view).loadRates(map.toList().map { Rate(it.first, it.second) })
        verify(view).hideProgressBar()
        verify(view).showList()
    }

    @Test
    fun `base value is updated after rate change`() {
        presenter.onAttach(view)
        val value = "2.1"
        presenter.onRateChanged(defaultBase, value)

        verify(view).updateBaseValue(value.toDouble(), defaultBase)
    }

    @Test
    fun `no interactions with view after detach`() {
        presenter.onDetach()

        val value = "2.1"
        presenter.onRateChanged(defaultBase, value)

        verifyNoMoreInteractions(view)
    }

    @Test
    fun `on refresh presenter starts updating data`() {
        presenter.onAttach(view)
        presenter.onRefresh()

        Thread.sleep(1100)

        verify(service).conversions(defaultBase)
    }
}