package com.arysugiarto.attendence.data.remote.model


import com.google.gson.annotations.SerializedName

data class EmployeResponse(
    @SerializedName("payload")
    val payload: List<Payload>,
    @SerializedName("links")
    val links: Links,
    @SerializedName("meta")
    val meta: Meta
) {
    data class Payload(
        @SerializedName("id")
        val id: String,
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

    data class Links(
        @SerializedName("first")
        val first: String,
        @SerializedName("last")
        val last: String,
        @SerializedName("prev")
        val prev: Any,
        @SerializedName("next")
        val next: Any
    )

    data class Meta(
        @SerializedName("current_page")
        val currentPage: Int,
        @SerializedName("from")
        val from: Int,
        @SerializedName("last_page")
        val lastPage: Int,
        @SerializedName("links")
        val links: List<Link>,
        @SerializedName("path")
        val path: String,
        @SerializedName("per_page")
        val perPage: Int,
        @SerializedName("to")
        val to: Int,
        @SerializedName("total")
        val total: Int
    ) {
        data class Link(
            @SerializedName("url")
            val url: String,
            @SerializedName("label")
            val label: String,
            @SerializedName("active")
            val active: Boolean
        )
    }
}