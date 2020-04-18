package com.katelee.podcast.model

import androidx.lifecycle.MutableLiveData
import com.katelee.podcast.network.Repository

/**
 * Created by Kate on 2020-04-16.
 */

class CastDetailViewModel: BaseViewModel() {
    private val repository = Repository()
    val castDetail: MutableLiveData<CastDetail> = MutableLiveData()
    var isLoading: MutableLiveData<Boolean> = MutableLiveData()
    var requestError: MutableLiveData<String> = MutableLiveData()

    fun fetchCastDetail() = request (
        onError = {
            isLoading.postValue(false)
            requestError.postValue(it.message)
        },
        execute = {
            isLoading.postValue( true)
            castDetail.postValue(repository.getCastDetail().data?.collection)
            isLoading.postValue( false)
        }
    )
}
