package ru.netology.nmedia.dto

import android.os.Parcel
import android.os.Parcelable
import android.transition.Visibility
import android.view.View
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Post(
    val id: Long,
    val author: String,
    val published: String,
    val content: String,
    val likedByMe: Boolean,
    val likes: Int,
    val shares:Int,
    val visibles: Int,
    val videoVisibility: Int = View.INVISIBLE,
    val video:String? = null
) : Parcelable