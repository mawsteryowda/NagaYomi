package com.example.nagayomi.Views.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.nagayomi.R
import com.example.nagayomi.Views.Adapters.VerticalPageAdapter.PageViewHolder
import com.squareup.picasso.Picasso

class VerticalPageAdapter: RecyclerView.Adapter<PageViewHolder>() {
    var hash: String = ""
    private var pages: List<String> = listOf()

    class PageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val page: ImageView = itemView.findViewById(R.id.ivChapterPage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PageViewHolder {
        return PageViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.chapter_page,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: PageViewHolder, position: Int) {
        val curPage = pages[position]

        Picasso.get()
            .load("https://uploads.mangadex.org/data/${hash}/${curPage}")
            .into(holder.page)
    }

    override fun getItemCount(): Int {
        return pages.size
    }

    fun submitData(hash: String, pages: List<String>) {
        this.hash = hash
        this.pages = pages
        notifyDataSetChanged()
    }
}
