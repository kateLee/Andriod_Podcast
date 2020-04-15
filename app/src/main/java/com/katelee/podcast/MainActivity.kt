package com.katelee.podcast

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.katelee.podcast.data.model.Cast
import com.katelee.podcast.data.model.MainViewModel
import com.katelee.podcast.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var viewModel: MainViewModel = MainViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.viewModel = viewModel

        val items = ArrayList<Cast> ()
        items.add(Cast())
        items.add(Cast())
        items.add(Cast())
        items.add(Cast())
        viewModel.castList.value = items
    }
}

@BindingAdapter("items")
fun bindRecyclerViewWithItemList(recyclerView: RecyclerView, itemList: ArrayList<Cast>?) {
    itemList?.let {
        if (recyclerView.adapter == null) {
            recyclerView.adapter = CastAdapter(itemList)
        } else {
            recyclerView.adapter.apply {
                when (this) {
                    is CastAdapter -> setCastList(it)
                }
            }
        }
    }
}
