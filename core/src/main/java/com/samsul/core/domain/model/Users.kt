package com.samsul.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Users(
    val id: Int?,
    val login: String?,
    val url: String?,
    val avatarUrl: String?,
    val name: String?,
    val bio: String?,
    val location: String?,
    val type: String?,
    val publicRepos: Int?,
    val followers: Int?,
    val following: Int?,
    var isFavorite: Boolean?
) : Parcelable
