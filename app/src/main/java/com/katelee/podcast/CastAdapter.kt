package com.katelee.podcast

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.katelee.podcast.model.Cast
import android.view.LayoutInflater
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.katelee.podcast.databinding.ItemCastBinding


/**
 * Created by Kate on 2020-04-15.
 */

class CastAdapter(private var castList: ArrayList<Cast>) : RecyclerView.Adapter<CastAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val listItemBinding : ItemCastBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context),
            R.layout.item_cast, parent, false
        )
        return ViewHolder(listItemBinding)
    }

    override fun getItemCount(): Int {
        return castList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val cast = castList[position]
        holder.bind(cast)
//        holder.itemView.setOnClickListener(View.OnClickListener {  })
    }

    class ViewHolder(private val binding: ItemCastBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(cast: Cast) {
            binding.cast = cast
            binding.executePendingBindings()
        }
    }

    fun setCastList(it: ArrayList<Cast>) {
        castList = it
        notifyDataSetChanged()
    }
}

@BindingAdapter("imageUrl")
fun bindImage(view: ImageView, url: String?) {
    if (!url.isNullOrEmpty()) {
        Glide.with(view.context).load(url).centerCrop().fitCenter().into(view)
    }
}