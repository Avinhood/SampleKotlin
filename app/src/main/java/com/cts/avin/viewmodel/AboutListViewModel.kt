package com.cts.avin.viewmodel

import android.util.Log

import androidx.lifecycle.MutableLiveData
import com.cts.avin.network.ApiService
import com.cts.avin.data.main.ListData
import io.reactivex.Single

import javax.inject.Inject

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class AboutListViewModel @Inject
constructor(private val repoRepository: ApiService) : BaseViewModel() {

    val mainListData = MutableLiveData<ListData>()
    private var disposable: CompositeDisposable? = null

    init {
        disposable = CompositeDisposable()
    }

    /*
     * Method to get get the List by hitting API.
     * */
    fun makeMainListCall() {
        progressDialog.postValue(true)
        disposable!!.add(repoRepository.mainList.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribeWith<Single>(object : DisposableSingleObserver<ListData>() {
                    override fun onSuccess(listData: ListData) {
                        mainListData.postValue(listData)
                        progressDialog.postValue(false)
                    }

                    override fun onError(e: Throwable) {
                        progressDialog.postValue(false)
                        errorMsg.postValue("")
                    }
                }))
    }

    override fun onCleared() {
        super.onCleared()
        if (disposable != null) {
            disposable!!.clear()
            disposable = null
        }
    }

}
