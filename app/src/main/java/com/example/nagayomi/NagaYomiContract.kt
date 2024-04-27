package com.example.nagayomi

import androidx.fragment.app.Fragment
import com.example.nagayomi.Models.Chapter.Data
import com.example.nagayomi.Models.Chapter.Feed
import com.example.nagayomi.Models.Manga.MangaResponse
import com.example.nagayomi.Models.Manga.Tag

interface NagaYomiContract {
    interface Model{
         suspend fun getCoverData() : String
         suspend fun getNagatoroData() : MangaResponse
         suspend fun getChapters(): Feed
         suspend fun getChapterHash(id: String): String
         suspend fun getChapterFiles(id: String): List<String>
    }

    interface HomeView{
        fun showCoverImage(url: String)
        fun showMangaInfo(title: String, altTitle: String, description: String, tags: ArrayList<Tag>)
        fun showChapters(chapters: List<Data>, isReversed: Boolean, feed: Feed)
        fun reverseChapterOrder()
        fun showError()

    }
    interface HomePresenter{
        suspend fun getCover()
        suspend fun getNagatoro()
        suspend fun getChapters()
        fun reverseOrder()
    }

    interface ChapterView{
        fun replaceFragment(fragment: Fragment)
        fun changeSupportActionBarTitle(chapter: String)
        fun showToast()
    }
    interface ChapterPresenter{
        fun orientationToggle()
        fun displayFragment(fragment: Fragment)
        fun nextChapterButtonClick()

    }
    interface PageView{
        fun showPages(hash: String, pages: List<String>)
    }
    interface PagePresenter{
        suspend fun getChapterPages(id: String)
        suspend fun getHash(id: String): String
        suspend fun getPageFiles(id: String): List<String>
    }

}