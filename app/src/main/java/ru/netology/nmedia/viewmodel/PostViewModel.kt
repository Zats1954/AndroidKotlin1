package ru.netology.nmedia.viewmodel

import android.app.Application
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.database.AppDb
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.repository.PostRepository
import ru.netology.nmedia.repository.PostRepositorySQLiteImpl

private val empty = Post(
    id = 0,
    content = "",
    author = "",
    likedByMe = false,
    published = "",
    likes = 0,
    shares = 0,
    visibles = 0,
    videoVisibility = View.INVISIBLE,
    video = " "
)

class PostViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: PostRepository = PostRepositorySQLiteImpl(AppDb.getInstance(application).postDao())
    val data = repository.getAll()
    val edited = MutableLiveData(empty)

    fun edit(post: Post) {
        edited.value = post
    }

    fun changeContent(content: String) {
        edited.value?.let {
            val text = content.trim()
            if (it.content == text) return
            val post = it.copy(content = text)
            repository.save(post)
            edited.value = post
        }
    }

    fun likeById(id: Long) = repository.likeById(id)

    fun removeById(id: Long) = repository.removeById(id)

    fun video(post: Post) {
        edited.value?.let {
            repository.addVideo(post)
            edited.value = post
        }
        }

    fun addPost() {
        edited.value = empty
     }
}