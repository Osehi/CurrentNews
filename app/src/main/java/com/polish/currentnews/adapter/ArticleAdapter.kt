package com.polish.currentnews.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.polish.currentnews.databinding.ListItemNewsarticleBinding
import com.polish.currentnews.model.Article
import com.polish.currentnews.ui.OnItemOpenWebListener
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.list_item_newsarticle.view.*

class ArticleAdapter(val onClickListener:OnClickListener, val onItemOpenWebListener:OnItemOpenWebListener):ListAdapter<Article, ArticleAdapter.ArticleViewHolder>(DiffCallback) {

    class ArticleViewHolder(var binding:ListItemNewsarticleBinding):RecyclerView.ViewHolder(binding.root) {

        fun bind(article:Article){
            binding.article = article

            // set image on the recyclerview
            Picasso.get()
                .load(article.urlToImage)
                .into(binding.articleImageId)


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

        holder.itemView.setOnClickListener {
            onClickListener.onClick(article)
        }

        holder.itemView.pubplisheAtId.setOnClickListener {
            onItemOpenWebListener.onItemOpenWeb(article)
        }


        holder.bind(article)

    }

    class OnClickListener(val clickListener:(article:Article) -> Unit){

       fun onClick(article: Article) = clickListener(article)

    }


}