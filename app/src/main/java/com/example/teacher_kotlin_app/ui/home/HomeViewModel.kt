package com.example.teacher_kotlin_app.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.teacher_kotlin_app.R

class HomeViewModel : ViewModel() {

    // ...

    private val _appPictures = MutableLiveData<List<Int>>()
    val appPictures: LiveData<List<Int>> get() = _appPictures

    fun fetchAppPictures() {
        // Simulate fetching app pictures (Replace this with actual data retrieval)
        val sampleAppPictures = listOf(R.drawable.app_picture_1, R.drawable.app_picture_2, R.drawable.app_picture_3)
        _appPictures.value = sampleAppPictures
    }
}