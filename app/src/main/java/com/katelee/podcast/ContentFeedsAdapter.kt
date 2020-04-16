package com.katelee.podcast

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.katelee.podcast.databinding.ItemContentFeedBinding
import com.katelee.podcast.model.CastDetail
import java.util.ArrayList

class ContentFeedsAdapter(private var it: ArrayList<CastDetail.ContentFeed>) : RecyclerView.Adapter<ContentFeedsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val listItemBinding: ItemContentFeedBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_content_feed, parent, false
        )
        return ViewHolder(listItemBinding)
    }

    override fun getItemCount(): Int {
        return it.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = it[position]
        holder.bind(item)
//        holder.itemView.setOnClickListener {
//            it.context.startActivity(PlayerActivity.getStartIntent(it.context, item.url))
//        }
    }

    class ViewHolder(private val binding: ItemContentFeedBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(contentFeed: CastDetail.ContentFeed) {
            binding.contentFeed = contentFeed
            binding.executePendingBindings()
        }
    }

    fun setContentFeedList(it: ArrayList<CastDetail.ContentFeed>) {
        this.it = it
        notifyDataSetChanged()
    }
}