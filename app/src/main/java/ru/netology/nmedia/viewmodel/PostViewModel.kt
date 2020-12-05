package ru.netology.nmedia.viewmodel

import androidx.lifecycle.ViewModel
import ru.netology.nmedia.repository.PostRepository
import ru.netology.nmedia.repository.PostRepositoryInMemoryImpl

class PostViewModel: ViewModel() {
    private val repository: PostRepository = PostRepositoryInMemoryImpl()
    val data = repository.get()
    fun like() = repository.like()
    inline  fun intToString(num: Int): String {
        val str = num.toString()
        when (str.length) {
            1, 2, 3 -> return str                     // 999
            4 -> return if (str[1] != '0') {
                str[0] + "."+ str[1]+"K"    // 1100
            } else {
                str[0] + "K"        //1000
            }
            5 -> return  str.substring(0,2) +"K"            // 10000
            6 -> return str.substring(0,3) +"K"        // 100000
            else -> return str.substring(0,str.length - 6)+"." + str[str.length - 6] +"M"           //1000000
        }
    }
}