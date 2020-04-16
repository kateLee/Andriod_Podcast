package com.katelee.podcast.model

import android.util.Log
import androidx.lifecycle.MutableLiveData

/**
 * Created by Kate on 2020-04-16.
 */

class PlayerViewModel: BaseViewModel() {
    var artworkUrl: String = ""
    var mediaName: String = ""
    var timeNow: MutableLiveData<String> = MutableLiveData()
//    var isLoading: MutableLiveData<Boolean> = MutableLiveData()

//    fun prepare() = request (
//        onError = {
//            // handle error
////            todo alert
////            isPlaying.value = false
//            Log.e("@v@PlayerViewModel", it.message)
//        },
//        execute = {
//            mediaPlayer?.prepareAsync()
////            isPlaying.postValue( true)
////            isPlaying.postValue( false)
//        }
//    )
}