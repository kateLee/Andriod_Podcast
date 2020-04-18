package com.katelee.podcast

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.katelee.podcast.model.Cast
import com.katelee.podcast.model.MainViewModel
import com.katelee.podcast.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        val viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        viewModel.fetchCasts()
        binding.swipeRefreshLayout.setOnRefreshListener { viewModel.refreshCasts() }
    }
}

@BindingAdapter("castItems")
fun bindRecyclerViewWithCastItemList(recyclerView: RecyclerView, itemList: ArrayList<Cast>?) {
    itemList?.let {
        if (recyclerView.adapter == null) {
            recyclerView.adapter = CastAdapter(it)
        } else {
            recyclerView.adapter?.apply {
                when (this) {
                    is CastAdapter -> setCastList(it)
                }
            }
        }
    }
}

@BindingAdapter("isRefreshing")
fun isRefreshing(swipeRefreshLayout: SwipeRefreshLayout, isLoading: Boolean) {
    if (!isLoading) swipeRefreshLayout.isRefreshing = false
}
