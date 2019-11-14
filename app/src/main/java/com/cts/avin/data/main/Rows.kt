package com.cts.avin.data.main

import com.google.gson.annotations.SerializedName

class Rows {

    @SerializedName("title")
    var title: String? = null
    @SerializedName("description")
    var description: String? = null
    @SerializedName("imageHref")
    var imageHref: String? = null
}
