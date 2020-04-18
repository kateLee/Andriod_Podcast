package com.katelee.podcast.model

import android.util.Log
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.delay

/**
 * Created by Kate on 2020-04-16.
 */

class PlayerViewModel: BaseViewModel() {
    var artworkUrl: String = ""
    var mediaName: String = ""
    var mediaUrl: String = ""
    var timerIsGoing = false
    var isPrepared: MutableLiveData<Boolean> = MutableLiveData()
    var timeUpdate: MutableLiveData<Boolean> = MutableLiveData()
    var timeNow: MutableLiveData<Int> = MutableLiveData()
    var duration: MutableLiveData<Int> = MutableLiveData()

    fun timerStart() = request (
        onError = {
            timerIsGoing = false
//            todo
            Log.e("@v@PlayerViewModel", it.message)
        },
        execute = {
            timerIsGoing = true
            delay(500)
            if (timerIsGoing) timeUpdate.postValue(true)
        }
    )

    fun timerEnd() { timerIsGoing = false }
}