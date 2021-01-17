package ru.netology.nmedia.activity

import android.content.Intent
import android.os.Bundle
import android.text.InputFilter
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.BaseTransientBottomBar.LENGTH_INDEFINITE
import com.google.android.material.snackbar.Snackbar
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.ActivityIntentHandlerBinding

class AppActivity : AppCompatActivity(R.layout.activity_intent_handler){
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        val binding = ActivityIntentHandlerBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        intent?.let{
//            if(it.action != Intent.ACTION_SEND){return@let}
//            val text = it.getStringExtra(Intent.EXTRA_TEXT)
//            if (text.isNullOrBlank()){
//                Snackbar.make(binding.root, "Content can't be empty",LENGTH_INDEFINITE)
//                    .setAction(android.R.string.ok){
//                        finish()
//                    }.show()
//                return@let
//            }
//        }
//    }
}