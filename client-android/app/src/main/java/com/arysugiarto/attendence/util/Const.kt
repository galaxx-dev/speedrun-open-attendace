package com.arysugiarto.attendence.util

import okhttp3.MediaType.Companion.toMediaTypeOrNull

object Const {


    object NETWORK {
        const val PREFIX_GENERAL = "v1/general/"
        const val SESSION_ID = "session_id"
        const val EQUALS = "="

        const val Survey = "api/v1/survey"

        object Media {
            const val UPLOAD = PREFIX_GENERAL + "file"
            const val FILE_FIELD = "file[]"
        }

    }

    object MediaType {
        val image = "image/jpeg".toMediaTypeOrNull()
        val text = "text/plain".toMediaTypeOrNull()
    }

    object Access {
        const val AUTH_PREFIX = "Bearer"
    }

    object File {
        object Location {
            const val basePath = "Paninti/"
            const val storePath = "Store/"
        }

        object MimeType {
            const val image = "image/jpeg"
            const val pdf = "application/pdf"
        }

        object Image {
            const val defaultFileName = "Paninti-Image"
        }

        object Pending {
            const val isPending = 1
            const val notPending = 0
        }

        object Type {
            const val IMAGE = "image"
        }

        object Variant {
            const val ORIGINAL = "original"
            const val THUMBNAIL = "thumbnail"
        }
    }

    object Paging {
        const val PER_PAGE_SMALL = 10
        const val PER_PAGE_MEDIUM = 25
        const val PER_PAGE_LARGE = 50
        const val BEGIN_PAGE = 1
        const val NOTIFICATION_PAGE = 4
    }

    object PRODUCT {
        const val NOT_AVAILABLE = "N/A"
    }

}