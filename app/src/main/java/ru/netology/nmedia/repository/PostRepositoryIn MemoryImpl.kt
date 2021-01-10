package ru.netology.nmedia.repository

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.dto.Post

class PostRepositoryInMemoryImpl : PostRepository {
    private var nextId = 1L
    private var posts = listOf(
        Post(
            id = nextId++,
            author = "Нетология. Университет интернет-знаний",
            published = "23 ноября в 20:00",
            content = "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия — помочь встать на путь роста и начать цепочку перемен → http://netolo.gy/fyb",
            likedByMe = false,
            likes = 10,
            shares = 1,
            visibles = 12340000
        ),
        Post(
            id = nextId++,
            author = "Песков рассказал, использует ли Путин интернет",
            published = "18 ноября в 15:44",
            content = "Пресс-секретарь президента России Дмитрий Песков в эфире радиостанции «Комсомольская правда» утвердительно ответил на вопрос, использует ли глава государства Владимир Путин интернет.Но смартфона у него нет.Ранее Песков заявил, что Путина нет в социальных сетях.",
            likedByMe = false,
            likes = 10,
            shares = 1,
            visibles = 123400
        ),
        Post(
            id = nextId++,
            author = "Нетология. Университет интернет-знаний",
            published = "18 декабря в 15:27",
            content = "Следователя Следственного комитета России Левона Агаджаняна задержали в Москве по подозрению в совершении ряда преступлений, в том числе привлечении невиновного к уголовной ответственности.Об этом информирует ТАСС со ссылкой на пресс-службу Басманного суда.",
            likedByMe = false,
            likes = 1,
            shares = 2,
            visibles = 12340
        )
    )
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
        posts = posts.filter { it.id != id }
        data.value = posts
    }

    override fun save(post: Post) {
        if (post.id == 0L) {
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
        posts = posts.map {
            if (it.id != post.id) it else it.copy(content = post.content)
        }
        data.value = posts
    }

    override fun addVideo(post: Post) {
        posts = posts.map {
            if (it.id != post.id) it else it.copy(
                video = post.video,
                videoVisibility = View.VISIBLE
            )
        }
        data.value = posts
    }
}