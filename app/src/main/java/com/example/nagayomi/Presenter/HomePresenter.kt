package com.example.nagayomi.Presenter

import com.example.nagayomi.Models.Manga.Tag
import com.example.nagayomi.NagaYomiContract
import java.io.IOException

class HomePresenter(
    private val view: NagaYomiContract.HomeView,
    private val model: NagaYomiContract.Model
) : NagaYomiContract.HomePresenter {
    private var isReversed = false
    override suspend fun getCover() {
        try {
            view.showCoverImage(model.getCoverData())
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    override suspend fun getNagatoro() {
        try {
            val responseData = model.getNagatoroData()
            val tags: ArrayList<Tag> = arrayListOf()
            val title = responseData.data.attributes.title.jaRo
            val altTitle = responseData.data.attributes.altTitles[14].en
            val description = responseData.data.attributes.description.en
            tags.addAll(responseData.data.attributes.tags)
            view.showMangaInfo(title, altTitle, description, tags)
        } catch (e: IOException) {
            e.printStackTrace()
            view.showError()
        }
    }

    override suspend fun getChapters() {
        val response = model.getChapters()
        if (!isReversed) {
            view.showChapters(response.data.sortedByDescending { it.attributes.chapter.toDouble() },
                isReversed,
                response)
        }
        else {
            view.showChapters(response.data.sortedBy { it.attributes.chapter.toDouble() },
                isReversed,
                response)
        }
    }

    override fun reverseOrder() {
        this.isReversed = !isReversed
        view.reverseChapterOrder()
    }
}