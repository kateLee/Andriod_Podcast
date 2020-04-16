package com.katelee.podcast.model

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.katelee.podcast.network.Repository

/**
 * Created by Kate on 2020-04-15.
 */

class MainViewModel: BaseViewModel() {
    private val repository = Repository()
    val castList: MutableLiveData<ArrayList<Cast>> = MutableLiveData()

    fun fetchCasts() = request (
        onError = {
            // handle error
//            todo alert
            Log.e("@v@MainViewModel", it.message)
        },
        execute = {
            castList.postValue(repository.getCasts().data?.podcast)
        }
    )
}