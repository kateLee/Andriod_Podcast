package com.katelee.podcast.data.model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


/**
 * Created by Kate on 2020-04-15.
 */

class MainViewModel: ViewModel() {
    val castList: MutableLiveData<ArrayList<Cast>> = MutableLiveData()
}