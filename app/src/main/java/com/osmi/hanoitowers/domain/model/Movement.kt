package com.osmi.hanoitowers.domain.model

data class Movement(
    val move: Int,
    val to: String,
    val from: String,
    val disk: Int
)
