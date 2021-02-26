package ru.netology.nmedia.viewmodel

import android.app.Application
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.model.FeedModel
import ru.netology.nmedia.repository.PostRepository
import ru.netology.nmedia.repository.PostRepositoryNetImpl
import ru.netology.nmedia.util.SingleLiveEvent
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.concurrent.thread

private var empty = Post(
    id = 0,
    content = "",
    author = "Аноним",
    likedByMe = false,
    published = "now",
    likes = 0,
    shares = 0,
    visibles = 0,
    videoVisibility = View.INVISIBLE,
    video = " "
)

class PostViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: PostRepository = PostRepositoryNetImpl()
    private val _data = MutableLiveData(FeedModel())
    val data: LiveData<FeedModel>
        get() = _data
    val edited = MutableLiveData(empty)
    private val _postCreated = SingleLiveEvent<Unit>()
    val postCreated: LiveData<Unit>
        get() = _postCreated

//    init {
//        loadPosts()
//    }

    fun loadPosts() {
        thread {
            _data.postValue(FeedModel(loading = true))
            try {
                val posts = repository.getAll()
                FeedModel(posts = posts, empty = posts.isEmpty())
            } catch (e: IOException) {
                FeedModel(error = true)
            }.also { _data::postValue }
        }
    }

    fun edit(post: Post) {
        edited.value = post
    }

    fun changeContent(content: String) {
        edited.value?.let {
            val text = content.trim()
            if (it.content == text) return
            val post = it.copy(content = text)
            thread{
            repository.save(post)
            _postCreated.postValue(Unit)
            }
            edited.value = post
        }
    }

    fun likeById(id: Long) {
        thread {
            repository.likeById(id)
        }
    }

    fun removeById(id: Long) {
        thread {
            val old = _data.value?.posts.orEmpty()
            _data.postValue(
                _data.value?.copy(
                    posts = _data.value?.posts.orEmpty()
                        .filter { it.id != id })
            )
            try {
                repository.removeById(id)
            } catch (e: IOException) {
                _data.postValue(_data.value?.copy(old))
            }
        }
    }

    fun video(post: Post) {
        edited.value?.let {
            repository.addVideo(post)
            edited.value = post
        }
    }

    fun addPost() {
        var newPost = empty
        newPost.let {
            thread {
                it.published =
                    SimpleDateFormat("dd MMMM yyyy hh:mm", Locale.getDefault()).format(Date())
                repository.save(it)
                _postCreated.postValue(Unit)
            }
        }
        edited.value = newPost
    }
}