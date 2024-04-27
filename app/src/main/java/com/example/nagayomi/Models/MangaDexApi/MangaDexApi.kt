package com.example.nagayomi.Models.MangaDexApi
import com.example.nagayomi.Models.Chapter.Feed
import com.example.nagayomi.Models.Cover.GetCover
import com.example.nagayomi.Models.Manga.MangaResponse
import com.example.nagayomi.Models.Pages.ChapterPages
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MangaDexApi {
    @GET("manga/d86cf65b-5f6c-437d-a0af-19a31f94ec55")
    suspend fun getMangaDetails(): MangaResponse

    @GET("cover/55e86e61-8206-4f61-862f-8879b535b295")
    suspend fun getCoverDetails(): GetCover

    @GET("manga/d86cf65b-5f6c-437d-a0af-19a31f94ec55/feed?translatedLanguage[]=en")
    suspend fun getFeed(
        @Query("limit") limit: Int
    ): Feed

    @GET("/at-home/server/{chapterId}")
    suspend fun getChapterData(
        @Path("chapterId") chapterId: String
    ): ChapterPages
}