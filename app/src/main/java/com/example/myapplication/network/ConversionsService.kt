package com.example.myapplication.network

import com.example.myapplication.model.Conversion
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface ConversionsService {
    @GET("latest")
    fun conversions(@Query("base") base: String): Observable<Conversion>
}
