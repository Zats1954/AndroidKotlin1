package ru.netology.nmedia

class Post(
    val id: Long,
    val author: String,
    val published: String,
    val content: String,
    var likedByMe: Boolean = false,
    var likes: Int,
    var shares:Int,
    var visibles: Int
) {}