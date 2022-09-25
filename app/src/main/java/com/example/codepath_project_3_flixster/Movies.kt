package com.example.codepath_project_3_flixster

import com.google.gson.annotations.SerializedName

class Movies(
    @SerializedName("overview")
    var overview: String? = null,

    @SerializedName("title")
    var title: String? = null,

    @SerializedName("release_date")
    var releaseDate: String? = null,

    @SerializedName("poster_path")
    var posterPath: String? = null

)