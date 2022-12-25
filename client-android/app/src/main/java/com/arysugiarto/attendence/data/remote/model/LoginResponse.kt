package com.arysugiarto.attendence.data.remote.model


import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("payload")
    val payload: Payload
) {
    data class Payload(
        @SerializedName("user")
        val user: User,
        @SerializedName("token")
        val token: String,
        @SerializedName("type")
        val type: String
    ) {
        data class User(
            @SerializedName("id")
            val id: Int,
            @SerializedName("employee_id")
            val employeeId: String,
            @SerializedName("is_active")
            val isActive: String,
            @SerializedName("deleted_at")
            val deletedAt: Any,
            @SerializedName("created_at")
            val createdAt: String,
            @SerializedName("updated_at")
            val updatedAt: String,
            @SerializedName("employee")
            val employee: Employee
        ) {
            data class Employee(
                @SerializedName("id")
                val id: Int,
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
                @SerializedName("deleted_at")
                val deletedAt: Any,
                @SerializedName("created_at")
                val createdAt: String,
                @SerializedName("updated_at")
                val updatedAt: String
            )
        }
    }
}