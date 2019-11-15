package com.cts.avin.data.main

import com.google.gson.annotations.SerializedName

class ListData {

    @SerializedName("title")
     var title: String? = null
    @SerializedName("rows")
     var rows: List<Rows>? = null
}
