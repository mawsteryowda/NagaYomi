package com.example.nagayomi.Views.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.nagayomi.Models.Chapter.Data
import com.example.nagayomi.Models.Chapter.Feed
import com.example.nagayomi.R
import com.example.nagayomi.Views.Adapters.ChapterAdapter.ChapterViewHolder

class ChapterAdapter(
    private val listener: OnItemClickListener,
    private val isReversed: Boolean
) : RecyclerView.Adapter<ChapterViewHolder>() {
    private lateinit var feed : List<com.example.nagayomi.Models.Chapter.Data>
    interface OnItemClickListener{
        fun onItemClick(data: com.example.nagayomi.Models.Chapter.Data, position: Int)
    }
    class ChapterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val chapterNumber: TextView = itemView.findViewById(R.id.tvChapterNumber)
        val title: TextView = itemView.findViewById(R.id.tvTitle)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChapterViewHolder {
        return ChapterViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.chapter_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ChapterViewHolder, position: Int) {
        val curChapter = feed[position]
        holder.chapterNumber.text = "Chapter ${ curChapter.attributes.chapter }"
        holder.title.text = curChapter.attributes.title
        holder.itemView.setOnClickListener {
            listener.onItemClick(curChapter, position)
        }
    }

    override fun getItemCount(): Int {
        return feed.size
    }
    fun submitFeed(chapters: Feed){
        if(!isReversed){
            val orderedChapters = chapters.data.sortedByDescending { it.attributes.chapter.toDouble() }
            this.feed = orderedChapters
        }else{
            val orderedChapters = chapters.data.sortedBy { it.attributes.chapter.toDouble() }
            this.feed = orderedChapters
        }
        notifyDataSetChanged()
    }
}