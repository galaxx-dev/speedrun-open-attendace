package com.arysugiarto.attendence.data.remote.model

import com.google.gson.annotations.SerializedName

data class SurveySend(
        @SerializedName("nama")
        val nama: String,
        @SerializedName("pekerjaan")
        val pekerjaan: String,
        @SerializedName("nilai")
        val nilai: String,
        @SerializedName("usia")
        val usia: String,
        @SerializedName("alasan")
        val alasan: String,
    )
