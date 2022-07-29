package com.calvin.internkumparantest.ui.userdetail

import DataRepository
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.calvin.internkumparantest.data.AlbumResponseItem
import com.calvin.internkumparantest.data.PhotoResponseItem
import com.calvin.internkumparantest.data.Resource
import com.calvin.internkumparantest.data.UserResponseItem
import kotlinx.coroutines.launch

class UserDetailViewModel(private val dataRepository: DataRepository) : ViewModel() {
    private val _user = MutableLiveData<Resource<List<UserResponseItem>>>()
    val user: LiveData<Resource<List<UserResponseItem>>>
        get() = _user

    private val _albums = MutableLiveData<Resource<List<AlbumResponseItem>>>()
    val albums: LiveData<Resource<List<AlbumResponseItem>>>
        get() = _albums

    private val _photos = MutableLiveData<Resource<List<PhotoResponseItem>>>()
    val photos: LiveData<Resource<List<PhotoResponseItem>>>
        get() = _photos

    fun getAlbums(userId: Int) {
        _albums.postValue(Resource.Loading(true))
        viewModelScope.launch {
            try {
                val response = dataRepository.getAlbums(userId)
                if(response.isSuccessful) {
                    _albums.postValue(Resource.Success(response.body()!!))
                } else {
                    _albums.postValue(Resource.Error("Something Went Wrong"))
                }
            } catch (e: Exception) {
                _albums.postValue(Resource.Error(e.message.toString()))
            }
        }
    }

    fun getPhotos(albumId: Int) {
        _photos.postValue(Resource.Loading(true))
        viewModelScope.launch {
            try {
                val response = dataRepository.getPhotos(albumId)
                if(response.isSuccessful) {
                    _photos.postValue(Resource.Success(response.body()!!))
                } else {
                    _photos.postValue(Resource.Error("Something Went Wrong"))
                }
            } catch (e: Exception) {
                _photos.postValue(Resource.Error(e.message.toString()))
            }
        }
    }

    fun getUserDetail(userId: Int) {
        _user.postValue(Resource.Loading(true))
        viewModelScope.launch {
            try {
                val response = dataRepository.getUserDetail(userId)
                if(response.isSuccessful) {
                    _user.postValue(Resource.Success(response.body()!!))
                } else {
                    _user.postValue(Resource.Error("Something Went Wrong"))
                }
            } catch (e: Exception) {
                _user.postValue(Resource.Error(e.message.toString()))
            }
        }
    }
}