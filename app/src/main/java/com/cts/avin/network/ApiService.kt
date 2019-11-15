package com.cts.avin.network

import com.cts.avin.data.main.ListData

import javax.inject.Inject

import io.reactivex.Single
import retrofit2.http.GET
import com.cts.avin.util.Constant


class ApiService @Inject
constructor(private val apiService: ApiInterface) {
    private val TAG = "ApiService"

    val mainList: Single<ListData>
        get() = apiService.mainList

    // =============================================================
    interface ApiInterface {
        @get:GET(Constant.LIST_URL)
        val mainList: Single<ListData>
    }

}
