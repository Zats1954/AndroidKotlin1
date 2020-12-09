package ru.netology.nmedia.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.CardPostBinding
import ru.netology.nmedia.dto.Post


typealias OnListener = (post: Post)-> Unit

class PostAdapter(
    private val onLikeListener: OnListener,
    private val onShareLIstener:OnListener
): RecyclerView.Adapter<PostViewHolder>() {
    var postsList = emptyList<Post>()
    set(value){
        field = value
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding = CardPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(binding, onLikeListener, onShareLIstener)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = postsList[position]
        holder.bind(post)
    }

    override fun getItemCount(): Int = postsList.size
}

class PostViewHolder(
    private val binding: CardPostBinding,
    private val onLikeListener: OnListener,
    private val onShareListener: OnListener
    ):RecyclerView.ViewHolder(binding.root) {

    fun bind(post:Post){

        binding.apply {
            tvAuthor.text = post.author
            tvPublished.text = post.published
            tvContent.text = post.content
            tvLikes.text =  intToString(post.likes)
            tvShares.text =  intToString(post.shares)
            tvVisibles.text =  intToString(post.visibles)
            ivLike.setImageResource(
                if(post.likedByMe) R.drawable.ic_heart2
                else R.drawable.ic_heart )
            ivLike.setOnClickListener {
                onLikeListener(post)}
            ivShare.setOnClickListener {
                onShareListener(post)}
        }
    }

    fun intToString(num: Int): String {
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
