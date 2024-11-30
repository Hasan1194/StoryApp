package com.dicoding.picodiploma.loginwithanimation.view

import androidx.lifecycle.ViewModel
import com.dicoding.picodiploma.loginwithanimation.data.StoriesRepository

class MapsViewModel(private val storiesRepository: StoriesRepository) : ViewModel() {
    fun getStoriesWithLocation() = storiesRepository.getStoriesWithLocation()
}