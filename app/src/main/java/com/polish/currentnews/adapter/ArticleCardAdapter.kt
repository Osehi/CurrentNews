package com.polish.currentnews.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.polish.currentnews.databinding.ListItemStandardnewslayoutBinding
import com.polish.currentnews.model.Article
import com.polish.currentnews.ui.OnItemOpenWebListener
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.list_item_standardnewslayout.view.*

class ArticleCardAdapter(val onClickListener:OnClickListener, val onItemOpenWebListener: OnItemOpenWebListener):ListAdapter<Article, ArticleCardAdapter.ArticleCardViewHolder>(DiffCallback) {

    class ArticleCardViewHolder(var binding: ListItemStandardnewslayoutBinding):RecyclerView.ViewHolder(binding.root){

        fun bind(article: Article){
            binding.article = article

            // set image on the recyclerview
            Picasso.get()
                .load(article.urlToImage)
                .into(binding.pictureMessage)



            binding.executePendingBindings()
        }

    }

   companion object DiffCallback: DiffUtil.ItemCallback<Article>(){
       override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
           return oldItem == newItem
       }

       override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
           return oldItem.content == newItem.content
       }
   }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleCardViewHolder {
        return ArticleCardViewHolder(ListItemStandardnewslayoutBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: ArticleCardViewHolder, position: Int) {
        val article = getItem(position)

        holder.itemView.setOnClickListener {
            onClickListener.onClick(article)
        }

//        holder.itemView.readMore.setOnClickListener {
//            onItemOpenWebListener.onItemOpenWeb(article)
//        }

        holder.bind(article)

    }


    class OnClickListener(val clickListener:(article:Article) -> Unit){

        fun onClick(article: Article) = clickListener(article)

    }


}