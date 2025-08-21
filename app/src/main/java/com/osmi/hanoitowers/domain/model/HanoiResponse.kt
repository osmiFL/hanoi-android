package com.osmi.hanoitowers.domain.model

import com.google.gson.annotations.SerializedName

data class HanoiResponse(
    @SerializedName("disks")
    val disks: Int,
    @SerializedName("has_next")
    val hasNext: Boolean,
    @SerializedName("has_prev")
    val hasPrev: Boolean,
    @SerializedName("movements")
    val movements: List<Movement>,
    @SerializedName("page")
    val page: Int,
    @SerializedName("per_page")
    val perPage: Int,
    @SerializedName("total_movements")
    val totalMovements: Int,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("urls")
    val urls: Urls
)

data class Urls(
    val first: String,
    val last: String,
    val next: String?,
    val prev: String?,
    val current: String
)
