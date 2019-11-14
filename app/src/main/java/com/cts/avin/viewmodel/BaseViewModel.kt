package com.cts.avin.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class BaseViewModel : ViewModel() {
    var progressDialog = MutableLiveData<Boolean>()
        internal set
    var errorMsg = MutableLiveData<String>()
        internal set
}
