package com.example.nagayomi.Views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.nagayomi.Models.Chapter.Data
import com.example.nagayomi.NagaYomiContract
import com.example.nagayomi.Presenter.ChapterPresenter
import com.example.nagayomi.R
import com.example.nagayomi.databinding.ActivityViewChapterBinding

class ViewChapter : AppCompatActivity(), ViewPagesHorizontal.NextChapterListener, NagaYomiContract.ChapterView {
    private lateinit var binding: ActivityViewChapterBinding
    private lateinit var chapterList: List<Data>
    private var index: Int = 0
    private var isReversed: Boolean = false
    private lateinit var presenter: ChapterPresenter
    override fun onCreate(savedInstanceState: Bundle?) {
        chapterList = (intent.getSerializableExtra("chapterList") as? Array<Data>)!!.toList()
        index = intent.getIntExtra("chapterIndex", 0)
        isReversed = intent.getBooleanExtra("isReversed", false)
        binding = ActivityViewChapterBinding.inflate(layoutInflater)
        presenter = ChapterPresenter(this, chapterList, index, this, isReversed)

        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setSupportActionBar(findViewById(R.id.appbar))

        presenter.displayFragment(ViewPagesHorizontal(chapterList[index].id, this))
        binding.switchOrientationToggle.setOnClickListener {
            presenter.orientationToggle()
        }
    }
    override fun onNextChapter() {
        presenter.nextChapterButtonClick()
    }

    override fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragFragmentDisplay, fragment)
            .commit()
    }

    override fun changeSupportActionBarTitle(chapter: String) {
        supportActionBar?.title = "Chapter ${chapter}"
    }

    override fun showToast() {
        Toast.makeText(this, "Wow, you've finished the latest chapter too, senpai.", Toast.LENGTH_LONG).show()
    }
}