package com.samsul.core.data.local.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "user")
data class UserEntity (
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "userId")
    val id: Int?,

    @ColumnInfo(name = "login")
    val login: String?,

    @ColumnInfo(name = "url")
    val url: String?,

    @ColumnInfo(name = "avatarUrl")
    val avatarUrl: String?,

    @ColumnInfo(name = "name")
    val name: String?,

    @ColumnInfo(name = "bio")
    val bio: String?,

    @ColumnInfo(name = "location")
    val location: String?,

    @ColumnInfo(name = "type")
    val type: String?,

    @ColumnInfo(name = "publicRepos")
    val publicRepos: Int?,

    @ColumnInfo(name = "followers")
    val followers: Int?,

    @ColumnInfo(name = "following")
    val following: Int?,

    @ColumnInfo(name = "isFavorite")
    val isFavorite: Boolean?,


)
