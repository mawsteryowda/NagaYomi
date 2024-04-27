package com.example.nagayomi.Views.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.nagayomi.Models.Manga.Tag
import com.example.nagayomi.R
import com.example.nagayomi.Views.Adapters.TagAdapter.TagViewHolder

class TagAdapter : RecyclerView.Adapter<TagViewHolder>() {
    private var tags: ArrayList<com.example.nagayomi.Models.Manga.Tag> = arrayListOf()
    class TagViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tag: TextView = itemView.findViewById(R.id.tvTag)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TagViewHolder {
        return TagViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.tag,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: TagViewHolder, position: Int) {
        val currentTag = tags[position]
        holder.tag.text = currentTag.attributes.name.en
    }

    override fun getItemCount(): Int {
        return tags.size
    }
    fun submitList(tags: ArrayList<com.example.nagayomi.Models.Manga.Tag>){
        this.tags = tags
        notifyDataSetChanged()
    }
}