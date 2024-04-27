package com.example.nagayomi.Views

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nagayomi.Views.Adapters.ChapterAdapter
import com.example.nagayomi.Views.Adapters.TagAdapter
import com.example.nagayomi.Models.Chapter.Data
import com.example.nagayomi.Models.Chapter.Feed
import com.example.nagayomi.Models.MangaDexApi.NagaYomiModel
import com.example.nagayomi.NagaYomiContract
import com.example.nagayomi.Presenter.HomePresenter
import com.example.nagayomi.R
import com.example.nagayomi.databinding.ActivityMainBinding
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.squareup.picasso.Picasso
import kotlinx.coroutines.launch
import java.io.IOException

class MainActivity : AppCompatActivity(), NagaYomiContract.HomeView {
    private lateinit var binding: ActivityMainBinding
    private lateinit var shimmerFrame: ShimmerFrameLayout
    private lateinit var showTagAdapter: TagAdapter
    private lateinit var presenter: HomePresenter
    private lateinit var showChapterAdapter: ChapterAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        presenter = HomePresenter(this, NagaYomiModel())
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        showTagAdapter = TagAdapter()

        shimmerFrame = binding.sfl
        shimmerFrame.visibility = View.VISIBLE
        getNagatoroData(presenter)
        getCover(presenter)
        getChapters(presenter)
    }

    private fun getNagatoroData(presenter: NagaYomiContract.HomePresenter) {
        lifecycleScope.launch {
            try{
                presenter.getNagatoro()
            } catch (e: IOException) {
                e.printStackTrace()
                startActivity(Intent(this@MainActivity, Error::class.java))
            }
        }
    }

    private fun getCover(presenter: NagaYomiContract.HomePresenter) {
        lifecycleScope.launch {
            try {
                presenter.getCover()
            } catch (e: IOException) {
                e.printStackTrace()
                startActivity(Intent(this@MainActivity, Error::class.java))
            }
        }
    }

    private fun getChapters(presenter: NagaYomiContract.HomePresenter) {
        lifecycleScope.launch {
            try {
                presenter.getChapters()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    override fun showCoverImage(url: String) {
        Picasso.get().load(url).into(binding.imageView)
    }

    override fun showMangaInfo(title: String, altTitle: String, description: String, tags: ArrayList<com.example.nagayomi.Models.Manga.Tag>) {
        shimmerFrame.stopShimmer()
        shimmerFrame.visibility = View.GONE
        binding.tvTitle.text = title
        binding.tvEnAlt.text = altTitle
        binding.tvDesc.text = description
        binding.pageCard.visibility = View.VISIBLE
        showTagAdapter.submitList(tags)
        binding.rvTags.adapter = showTagAdapter
        binding.rvTags.layoutManager = LinearLayoutManager(
            this@MainActivity,
            LinearLayoutManager.HORIZONTAL,
            false
        )
    }

    override fun showChapters(chapters: List<Data>, isReversed: Boolean, feed: Feed) {
        showChapterAdapter = ChapterAdapter(object : ChapterAdapter.OnItemClickListener {
            override fun onItemClick(data: Data, position: Int) {
                startActivity(Intent(this@MainActivity, ViewChapter::class.java).apply {
                    putExtra("chapterIndex", position)
                    putExtra("isReversed", isReversed)
                    putExtra("chapterList", chapters.toTypedArray())
                })
            }
        },
            isReversed)
        showChapterAdapter.submitFeed(feed)
        binding.rvChapters.adapter = showChapterAdapter
        binding.rvChapters.layoutManager = LinearLayoutManager(this@MainActivity)
        binding.reverse.setOnClickListener {
            presenter.reverseOrder()
        }
    }

    override fun reverseChapterOrder() {
        getChapters(presenter)
    }

    override fun showError() {
        MaterialAlertDialogBuilder(this)
            .setView(R.layout.error_img)
            .setTitle("Error")
            .setMessage("Something went wrong, Senpai...")
            .setNegativeButton("Exit NagaYomi") { dialog, which ->
                this.finishAffinity();
            }
            .setPositiveButton("Refresh") { dialog, which ->
                triggerRestart(this)
            }
            .show()
    }
    fun triggerRestart(context: Activity) {
        val intent = Intent(context, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
        if (context is Activity) {
            context.finish()
        }
        Runtime.getRuntime().exit(0)
    }
}


