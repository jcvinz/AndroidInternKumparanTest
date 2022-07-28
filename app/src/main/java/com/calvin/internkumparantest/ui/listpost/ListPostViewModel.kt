package com.calvin.internkumparantest.ui.listpost

import DataRepository
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.calvin.internkumparantest.data.*
import kotlinx.coroutines.launch

class ListPostViewModel(private val dataRepository: DataRepository) : ViewModel() {

    private val _posts = MutableLiveData<Resource<List<PostResponseItem>>>()
    val posts: LiveData<Resource<List<PostResponseItem>>>
        get() = _posts

    private val _users = MutableLiveData<Resource<List<UserResponseItem>>>()
    val users: LiveData<Resource<List<UserResponseItem>>>
        get() = _users

    fun getAllUser() {
        _users.postValue(Resource.Loading(true))
        viewModelScope.launch {
            try {
                val response = dataRepository.getAllUser()
                if (response.isSuccessful) {
                    _users.postValue(Resource.Success(response.body()!!))
                } else {
                    _users.postValue(Resource.Error("Something Went Wrong"))
                }
            } catch (e: Exception) {
                _users.postValue(Resource.Error(e.message.toString()))
            }
        }
    }

    fun getAllPost() {
        _posts.postValue(Resource.Loading(true))
        viewModelScope.launch {
            try {
                val response = dataRepository.getAllPost()
                if (response.isSuccessful) {
                    _posts.postValue(Resource.Success(response.body()!!))
                } else {
                    _posts.postValue(Resource.Error("Something Went Wrong"))
                }
            } catch (e: Exception) {
                _posts.postValue(Resource.Error(e.message.toString()))
            }
        }
    }


}