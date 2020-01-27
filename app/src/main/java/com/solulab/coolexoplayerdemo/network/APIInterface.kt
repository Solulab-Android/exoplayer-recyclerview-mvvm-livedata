package com.solulab.coolexoplayerdemo.network

import com.solulab.coolexoplayerdemo.view.HomeData
import io.reactivex.Observable
import retrofit2.http.GET

interface APIInterface {


    @GET("5e2adf4b32000077001c6e9e")
    fun getHomeList(): Observable<BaseModel<List<HomeData>>>

}
