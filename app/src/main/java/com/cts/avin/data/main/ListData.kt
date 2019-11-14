package com.cts.avin.data.main

import com.google.gson.annotations.SerializedName

class ListData {

    @SerializedName("title")
    var title: String
    @SerializedName("rows")
    var rows: List<Rows>
}
