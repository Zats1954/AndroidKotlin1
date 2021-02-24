package ru.netology.nmedia.repository

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import ru.netology.nmedia.dto.Post
import java.lang.RuntimeException
import java.util.concurrent.TimeUnit

class PostRepositoryNetImpl: PostRepository {
    private val client = OkHttpClient.Builder()
        .connectTimeout(10, TimeUnit.SECONDS)
        .build()
    private val gson = Gson()
    private val typeToken = object: TypeToken<List<Post>>(){}
    companion object {
//        private const val BASE_URL = "http://10.0.2.2:9999"
        private const val BASE_URL = "http://192.168.0.136:9999"
        private val jsonType = "application/json".toMediaType()
    }

    override fun likeById(id: Long) {
        TODO("Not yet implemented")
    }

    override fun getAll(): List<Post> {
        val request: Request =   Request.Builder()
            .url("${BASE_URL}/api/posts")
            .build()
        return client.newCall(request)
            .execute()
            .let{
                it.body?.string() ?:
                throw RuntimeException("body is null")}
            .let{
                gson.fromJson(it, typeToken.type)
            }
    }

    override fun removeById(id: Long) {
       val request: Request = Request.Builder()
           .delete()
           .url("${BASE_URL}/api/posts/$id")
           .build()
        client.newCall(request)
            .execute()
            .close()

    }

    override fun save(post: Post) {
        val request: Request = Request.Builder()
            .post(gson.toJson(post).toRequestBody(jsonType))
            .url("${BASE_URL}/api/posts")
            .build()
        client.newCall(request).execute().close()

    }

    override fun addVideo(post: Post) {
        TODO("Not yet implemented")
    }

}