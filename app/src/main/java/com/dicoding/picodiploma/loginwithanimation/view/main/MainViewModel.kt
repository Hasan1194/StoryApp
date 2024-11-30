package com.dicoding.picodiploma.loginwithanimation.view.main

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.dicoding.picodiploma.loginwithanimation.data.StoriesRepository
import com.dicoding.picodiploma.loginwithanimation.data.UserRepository
import com.dicoding.picodiploma.loginwithanimation.data.pref.UserModel
import kotlinx.coroutines.launch
import java.io.File

class MainViewModel(
    private val repository: UserRepository,
    private val storiesRepository: StoriesRepository
) : ViewModel() {
    private var _currentImageUri = MutableLiveData<Uri?>()
    val currentImageUri: MutableLiveData<Uri?> = _currentImageUri

    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }

    //fun getAllStories() = storiesRepository.getAllStories()

    fun getPagedStories() = storiesRepository.getPagedStories().cachedIn(viewModelScope)

    fun logout() {
        viewModelScope.launch {
            repository.logout()
        }
    }

    fun uploadStory(imageFile: File, desc: String) = storiesRepository.uploadStory(imageFile, desc)

    fun setCurrentImageUri(uri: Uri?) {
        _currentImageUri.value = uri
    }
}