package com.arysugiarto.attendence.data.remote.model


import com.google.gson.annotations.SerializedName

data class RegisterModel(
    @SerializedName("employee_id")
    val employeeId: String,
    @SerializedName("fullname")
    val fullname: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("phone")
    val phone: String,
    @SerializedName("address")
    val address: String,
    @SerializedName("gender")
    val gender: String,
    @SerializedName("password")
    val password: String
)