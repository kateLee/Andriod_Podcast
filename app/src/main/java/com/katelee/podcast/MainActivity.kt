package com.katelee.podcast

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.katelee.podcast.data.model.Cast
import com.katelee.podcast.data.model.MainViewModel
import com.katelee.podcast.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        val viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        viewModel.fetchCasts()
    }
}

@BindingAdapter("items")
fun bindRecyclerViewWithItemList(recyclerView: RecyclerView, itemList: ArrayList<Cast>?) {
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
