package com.example.mviproject.viewmodel

import com.example.mviproject.model.ImageData

sealed class MainState {
    object Idle : MainState()
    object Loading : MainState()
    data class Data(val imageData : ArrayList<ImageData>) : MainState()
    data class Error(val error : String) : MainState()
}