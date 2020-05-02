package com.polish.currentnews.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.polish.currentnews.databinding.ListItemNewsarticleBinding
import com.polish.currentnews.model.Article

class ArticleAdapter():ListAdapter<Article, ArticleAdapter.ArticleViewHolder>(DiffCallback) {

    class ArticleViewHolder(var binding:ListItemNewsarticleBinding):RecyclerView.ViewHolder(binding.root) {

        fun bind(article:Article){
            binding.article = article
            binding.executePendingBindings()
        }

    }

    companion object DiffCallback:DiffUtil.ItemCallback<Article>(){
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.title == newItem.title
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        return ArticleViewHolder(ListItemNewsarticleBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article = getItem(position)

        holder.bind(article)

    }


}