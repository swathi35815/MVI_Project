package com.example.mviproject.viewmodel

sealed class MainIntent {
    object FetchData : MainIntent()
}