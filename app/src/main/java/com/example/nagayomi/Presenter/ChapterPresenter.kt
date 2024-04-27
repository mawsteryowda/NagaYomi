package com.example.nagayomi.Presenter

import androidx.fragment.app.Fragment
import com.example.nagayomi.Models.Chapter.Data
import com.example.nagayomi.NagaYomiContract
import com.example.nagayomi.Views.ViewPagesHorizontal
import com.example.nagayomi.Views.ViewPagesVertical

class ChapterPresenter(
    private val view: NagaYomiContract.ChapterView,
    private val chapters: List<Data>,
    private var index: Int,
    private val listener: ViewPagesHorizontal.NextChapterListener,
    private val isReversed: Boolean
) : NagaYomiContract.ChapterPresenter {
    private var orientation = 0
    override fun orientationToggle() {
        orientation = if (orientation == 0) {
            view.replaceFragment(ViewPagesVertical(chapters[index].id, listener))
            1
        } else {
            view.replaceFragment(ViewPagesHorizontal(chapters[index].id, listener))
            0
        }
    }

    override fun displayFragment(fragment: Fragment) {
        view.replaceFragment(fragment)
        view.changeSupportActionBarTitle(chapters[index].attributes.chapter)
    }

    override fun nextChapterButtonClick() {
        if (!isReversed) {
            index--
            if (index >= 0) {
                view.replaceFragment(ViewPagesHorizontal(chapters[index].id, listener))
                view.changeSupportActionBarTitle(chapters[index].attributes.chapter)
            } else {
                index = 0
                view.showToast()
            }
        } else {
            index++
            if (index <= chapters.size) {
                view.replaceFragment(ViewPagesHorizontal(chapters[index].id, listener))
                view.changeSupportActionBarTitle(chapters[index].attributes.chapter)
            } else {
                index = chapters.size - 1
                view.showToast()
            }
        }
        orientation = 0
    }
}