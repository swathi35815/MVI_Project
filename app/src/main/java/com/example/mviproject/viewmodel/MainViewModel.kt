package com.example.mviproject.viewmodel

import android.media.Image
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mviproject.api.ApiImageClient
import com.example.mviproject.model.ImageData
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {
    val userIntent = Channel<MainIntent>(Channel.UNLIMITED)
    private val _state = MutableStateFlow<MainState>(MainState.Idle)
    val state : StateFlow<MainState>
        get() = _state

    init {
        handleIntent()
    }

    private fun handleIntent() {
        viewModelScope.launch{
            userIntent.consumeAsFlow().collect{collector ->
                when(collector) {
                    is MainIntent.FetchData -> fetchImageData()
                }
            }

        }
    }

    private fun fetchImageData() {
        viewModelScope.launch {
            _state.value = MainState.Loading

            ApiImageClient.retrofitBuilder.getData()
                .enqueue(object : Callback<ArrayList<ImageData>>{
                    override fun onResponse(
                        call: Call<ArrayList<ImageData>>,
                        response: Response<ArrayList<ImageData>>
                    ) {
                        val data = response.body()
                        if(data != null) {
                            _state.value = MainState.Data(data)
                        }
                        else {
                            _state.value = MainState.Error("Empty Response body")
                        }
                    }

                    override fun onFailure(call: Call<ArrayList<ImageData>>, t: Throwable) {
                        _state.value = MainState.Error(t.toString())
                    }

                })
        }
    }
}