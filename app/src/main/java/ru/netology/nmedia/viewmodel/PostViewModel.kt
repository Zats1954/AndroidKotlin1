package ru.netology.nmedia.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.repository.PostRepository
import ru.netology.nmedia.repository.PostRepositoryInMemoryImpl

private val empty = Post(
    id = 0,
    content = "",
    author = "",
    likedByMe = false,
    published = "",
    likes = 0,
    shares = 0,
    visibles = 0
)

class PostViewModel: ViewModel() {
    private val repository: PostRepository = PostRepositoryInMemoryImpl()
    val data = repository.getAll()
    val edited = MutableLiveData(empty)

    fun edit(post: Post){
        repository.save(post)
        edited.value = post
    }

//    fun changeContent(content: String){
//        edited.value?.let{
//            val text = content.trim()
//            if(it.content == text) return
//            edited.value = it.copy(content = text)
//        }
//
//    }
    fun share(id: Long) =repository.share(id)
    fun likeById(id: Long) = repository.likeById(id)
    fun removeById(id: Long) = repository.removeById(id)
    fun video(post:Post){
        repository.addVideo(post)
        edited.value = post
    }
}