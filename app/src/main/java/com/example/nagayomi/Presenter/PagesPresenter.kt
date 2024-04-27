package com.example.nagayomi.Presenter

import com.example.nagayomi.NagaYomiContract

class PagesPresenter(
    private val view: NagaYomiContract.PageView,
    private val model: NagaYomiContract.Model
) : NagaYomiContract.PagePresenter {
    override suspend fun getChapterPages(id: String) {
        val hash = getHash(id)
        val pages = getPageFiles(id)
        view.showPages(hash, pages)
    }

    override suspend fun getHash(id: String): String {
        return model.getChapterHash(id)
    }

    override suspend fun getPageFiles(id: String): List<String> {
        return model.getChapterFiles(id)
    }
}