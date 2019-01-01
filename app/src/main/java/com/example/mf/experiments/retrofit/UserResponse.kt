package com.example.mf.experiments.retrofit

import com.squareup.moshi.Json

data class UserResponse(
    @field:Json(name = "id") val id : String,
    @field:Json(name = "name") val name : String,
    @field:Json(name = "avatar") val avatarURI : String
)