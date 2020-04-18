package com.katelee.podcast

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.katelee.podcast.databinding.ActivityCastDetailBinding
import com.katelee.podcast.model.CastDetail
import com.katelee.podcast.model.CastDetailViewModel
import androidx.recyclerview.widget.DividerItemDecoration

/**
 * Created by Kate on 2020-04-16.
 */

class CastDetailActivity : AppCompatActivity() {

    companion object {
        private const val EXTRA_ID = "EXTRA_ID"

        fun getStartIntent(context: Context, id: String): Intent {
            return Intent(context, CastDetailActivity::class.java)
                .putExtra(EXTRA_ID, id)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivityCastDetailBinding = DataBindingUtil.setContentView(this, R.layout.activity_cast_detail)
        val viewModel = ViewModelProvider(this).get(CastDetailViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        viewModel.fetchCastDetail()
        val itemDecoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        itemDecoration.setDrawable(ContextCompat.getDrawable(this, R.drawable.diveder)!!)

        binding.contentFeedList.addItemDecoration(itemDecoration)

        viewModel.requestError.observe(this, Observer<String> {
            AlertDialog.Builder(this)
                .setMessage(it)
                .setPositiveButton(R.string.retry) { _, _ -> viewModel.fetchCastDetail() }
                .setNegativeButton(R.string.finish) { _, _ -> finish() }
                .show()
        })
    }
}

@BindingAdapter(value = ["contentFeeds", "artworkUrl"])
fun bindRecyclerViewWithItemList(recyclerView: RecyclerView, itemList: ArrayList<CastDetail.ContentFeed>?, artworkUrl: String?) {
    itemList?.let {
        if (recyclerView.adapter == null) {
            recyclerView.adapter = ContentFeedsAdapter(it, artworkUrl)
        } else {
            recyclerView.adapter?.apply {
                when (this) {
                    is ContentFeedsAdapter -> setContentFeedList(it).setArtworkUrl(artworkUrl)
                }
            }
        }
    }
}