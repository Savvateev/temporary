package ru.netology.nmedia

import android.os.Bundle
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import ru.netology.nmedia.databinding.ActivityMainBinding
import java.math.RoundingMode
import kotlin.math.truncate

data class Post
    (val id: Int,
     val author: String, val content: String,
     val published: String, var likedByMe: Boolean,
     var countOfLikes : Int, var countOfShare : Int, var countOfView : Int)

fun toView(count : Int) : String {
    return when (count) {
        in 1..999 -> count.toString()
        in 1000..1099 -> "1K "
        in 1100..10000 -> ((count.toFloat()/1000).toBigDecimal().setScale(1,RoundingMode.DOWN).toString()) + "K "
        in 10001..999999 -> ((count.toFloat()/1000).toBigDecimal().setScale(0,RoundingMode.DOWN).toString()) + "K "
        in 1000000..1099000 -> "1M "
        in 1100000..100000000 -> ((count.toFloat()/1000000).toBigDecimal().setScale(0, RoundingMode.DOWN)).toString() + "M "
        else -> "0"
    }
}

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val post = Post (
            id = 1,
            author = "Нетология. Университет интернет-профессий будущего",
            content = "Привет, это новая Нетология! Когда-то Нетология начиналась с интенс ... ",
            published = "21 мая в 18:36",
            likedByMe = false,
            countOfLikes = 11,
            countOfShare = 1110000,
            countOfView = 1199
        )
        with (binding) {
            author.text = post.author
            published.text = post.published
            PostTextView.text = post.content
            countOfLikesView.text = toView(post.countOfLikes)
            countOfSharesView.text = toView(post.countOfShare)
            countOfVView.text = toView(post.countOfView)
            if (post.likedByMe) {
                likes?.setImageResource(R.drawable.baseline_favorite_24)
            }
            likes?.setOnClickListener {
                post.likedByMe = !post.likedByMe
                if (post.likedByMe) post.countOfLikes += 1
                else post.countOfLikes -= 1
                countOfLikesView.text = toView(post.countOfLikes)
                likes.setImageResource(
                    if (post.likedByMe) R.drawable.baseline_favorite_24
                    else  R.drawable.baseline_favorite_border_24
                )
            }
            share?.setOnClickListener {
                post.countOfShare += 1
                countOfSharesView.text = toView(post.countOfShare)
            }
        }
    }
}
