package com.katelee.podcast.model

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.katelee.podcast.network.Repository

/**
 * Created by Kate on 2020-04-16.
 */

class CastDetailViewModel: BaseViewModel() {
    private val repository = Repository()
    val castDetail: MutableLiveData<CastDetail> = MutableLiveData()

    fun fetchCastDetail() = request (
        onError = {
            // handle error
//            todo alert
            Log.e("@v@CastDetailViewModel", it.message)
        },
        execute = {
            castDetail.postValue(repository.getCastDetail().data?.collection)
        }
    )
}
