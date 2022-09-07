package com.samsul.core.data.remote.response

import com.google.gson.annotations.SerializedName

data class ListResponse(
    @field:SerializedName("items") val items: List<ListDataResponse>
)
