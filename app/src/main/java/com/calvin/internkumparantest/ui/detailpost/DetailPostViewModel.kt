package com.calvin.internkumparantest.ui.detailpost

import DataRepository
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.calvin.internkumparantest.data.CommentResponseItem
import com.calvin.internkumparantest.data.Resource
import kotlinx.coroutines.launch

class DetailPostViewModel(private val dataRepository: DataRepository) : ViewModel() {

    private val _comments = MutableLiveData<Resource<List<CommentResponseItem>>>()
    val comments: LiveData<Resource<List<CommentResponseItem>>>
        get() = _comments

    fun getComments(postId: Int) {
        _comments.postValue(Resource.Loading(true))
        viewModelScope.launch {
            try {
                val response = dataRepository.getComments(postId)
                if (response.isSuccessful) {
                    _comments.postValue(Resource.Success(response.body()!!))
                } else {
                    _comments.postValue(Resource.Error("Something Went Wrong"))
                }
            } catch (e: Exception) {
                _comments.postValue(Resource.Error(e.message.toString()))
            }
        }
    }
}