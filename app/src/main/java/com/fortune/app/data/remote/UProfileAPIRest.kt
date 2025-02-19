package com.fortune.app.data.remote

import com.fortune.app.BuildConfig
import com.fortune.app.data.db.entities.UProfileEntity
import retrofit2.http.POST

interface UProfileAPIRest {
    @POST("`${BuildConfig.API_URL}` + /userprofile/generateProfile")
    fun userProfile(id: Long, name: String, address: String, telf: String): UProfileEntity
}