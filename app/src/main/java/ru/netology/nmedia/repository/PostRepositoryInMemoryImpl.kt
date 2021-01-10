package ru.netology.nmedia.repository

import android.content.Context
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ru.netology.nmedia.dto.Post

class PostRepositoryInMemoryImpl(context: Context) : PostRepository {
    private companion object{
        const val POST_FILE = "posts.json"
    }
    private var nextId = 1L
    private val preferences = context.getSharedPreferences(POST_FILE, Context.MODE_PRIVATE)
    private val type = TypeToken.getParameterized(List::class.java,Post::class.java).type
    private val gson = Gson()

//    private val file = context.filesDir.resolve(POST_FILE)
//    private var posts: List<Post> = file.exists().let{exists ->
//        if(exists){
//            gson.fromJson(file.readText(),type)
//        } else {
//            emptyList<>()
//        }
//    }
    private var posts: List<Post> = preferences.getString(POST_FILE, null)?.let{
        gson.fromJson(it, type)
    }?: emptyList()
        set(value){
            field = value
            sync()
        }
    private val data = MutableLiveData(posts)
    override fun getAll(): LiveData<List<Post>> = data

    override fun likeById(id: Long) {
        posts = posts.map {
            if (it.id != id) it
            else it.copy(
                likedByMe = !it.likedByMe,
                likes = if (it.likedByMe) it.likes - 1
                else it.likes + 1
            )
        }
        data.value = posts
    }

    override fun share(id: Long) {
        posts = posts.map {
            if (it.id != id) it
            else it.copy(
                shares = it.shares + 1
            )
        }
        data.value = posts
    }

    override fun removeById(id: Long) {
        posts = posts.filter{it.id !=id}
        data.value = posts
    }

    override fun save(post: Post) {
        if(post.id == 0L){
            posts = posts + listOf(
                                     post.copy(
                                         id = nextId++,
                                         author = "Me",
                                         published = "now"
                                     )
                             )
            data.value = posts
            return
        }
        posts = posts.map{
            if(it.id != post.id) it else it.copy(content = post.content)
        }
        data.value = posts
    }

    override fun addVideo(post: Post) {
        posts = posts.map{
            if(it.id != post.id) it else it.copy(video = post.video, videoVisibility = View.VISIBLE)
        }
        data.value = posts
    }
    private fun sync() {
//        file.writeText(gson.toJson(posts))
        preferences.edit()
            .putString(POST_FILE,gson.toJson(posts))
            .apply()
    }
}