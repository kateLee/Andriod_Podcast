package com.katelee.podcast.model

import androidx.lifecycle.MutableLiveData
import com.katelee.podcast.network.Repository

/**
 * Created by Kate on 2020-04-15.
 */

class MainViewModel: BaseViewModel() {
    private val repository = Repository()
    val castList: MutableLiveData<ArrayList<Cast>> = MutableLiveData()
    var isLoading: MutableLiveData<Boolean> = MutableLiveData()
    var isRefreshing: MutableLiveData<Boolean> = MutableLiveData()
    var requestError: MutableLiveData<String> = MutableLiveData()

    fun fetchCasts() = request (
        onError = {
            isLoading.postValue(false)
            requestError.postValue(it.message)
        },
        execute = {
            isLoading.postValue( true)
            castList.postValue(repository.getCasts().data?.podcast)
            isLoading.postValue( false)
        }
    )

    fun refreshCasts() = request (
        onError = {
            isRefreshing.postValue( false)
            requestError.postValue(it.message)
        },
        execute = {
            isRefreshing.postValue( true)
            castList.postValue(repository.getCasts().data?.podcast)
            isRefreshing.postValue( false)
        }
    )
}