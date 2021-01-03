package ru.netology.nmedia.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import ru.netology.nmedia.databinding.ActivityNewBinding
import ru.netology.nmedia.dto.Post


class NewActivity : AppCompatActivity() {

    companion object {
     const val POST_KEY = "post"
    }

     override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityNewBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
        binding.ok.setOnClickListener {
             val text = binding.edit.text?.toString()
             if(text.isNullOrBlank()){
                 setResult(RESULT_CANCELED)
                 } else {
                     val intent = Intent()
                         .putExtra(POST_KEY, Post(
                             0L,
                             "",
                             "",
                              text,
                             false,
                             0,
                             0,
                             0
                             ))
                     setResult(RESULT_OK,intent)
                 }
             finish()
        }
    }
}