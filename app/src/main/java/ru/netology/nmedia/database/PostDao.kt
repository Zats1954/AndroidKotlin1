package ru.netology.nmedia.database

import androidx.lifecycle.LiveData
import androidx.room.*
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.entity.PostEntity

@Dao
interface PostDao {
    @Query("SELECT * FROM PostEntity ORDER BY id")
    fun getAll(): LiveData<List<PostEntity>>
    @Insert
    fun insert(post: PostEntity)

    @Query("UPDATE PostEntity SET content = :content WHERE id = :id")
    fun updateContentById(id: Long, content: String)

    fun save(post:PostEntity) =
    if(post.id == 0L) insert (post) else updateContentById(post.id,post.content)

    @Query("""
                UPDATE PostEntity SET
                 likes = likes + CASE WHEN likedByMe THEN -1 ELSE 1 END,
                 likedByMe = CASE WHEN likedByMe THEN 0  ELSE 1 END
                 WHERE id = :id                
                """)
    fun likeById(id:Long)

//    fun share(id: Long)

    @Query("DELETE FROM PostEntity WHERE id = :id")
    fun removeById(id:Long)

    @Query("UPDATE PostEntity SET video = :video, videoVisibility = :videoVisibility" +
                 "  WHERE id = :id")
    fun updateVideoById(id: Long, video: String, videoVisibility: Int)

    fun addVideo(post: Post?) =
        post?. let{
            it.video?.let { it1 ->
                updateVideoById(
                    it.id , it1, it.videoVisibility)
            }
        }
}