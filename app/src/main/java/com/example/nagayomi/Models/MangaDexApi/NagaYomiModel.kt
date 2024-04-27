package com.example.nagayomi.Models.MangaDexApi

import com.example.nagayomi.Models.Chapter.Data
import com.example.nagayomi.Models.Chapter.Feed
import com.example.nagayomi.Models.Manga.MangaResponse
import com.example.nagayomi.NagaYomiContract

class NagaYomiModel : NagaYomiContract.Model {
    override suspend fun getCoverData(): String {
        return "https://uploads.mangadex.org/covers/d86cf65b-5f6c-437d-a0af-19a31f94ec55/${ApiClient.apiService.getCoverDetails().data.attributes.fileName}"
    }

    override suspend fun getNagatoroData(): MangaResponse {
        return ApiClient.apiService.getMangaDetails()
    }

    override suspend fun getChapters(): Feed {
        val response = ApiClient.apiService.getFeed(1)
        val limit = response.total
        return ApiClient.apiService.getFeed(limit)
    }

    override suspend fun getChapterHash(id: String): String {
        return ApiClient.apiService.getChapterData(id).chapter.hash
    }

    override suspend fun getChapterFiles(id: String): List<String> {
        return ApiClient.apiService.getChapterData(id).chapter.data
    }

}