package com.calvin.internkumparantest.ui

import DataRepository
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.calvin.internkumparantest.di.Injection
import com.calvin.internkumparantest.ui.detailpost.DetailPostViewModel
import com.calvin.internkumparantest.ui.listpost.ListPostViewModel
import com.calvin.internkumparantest.ui.userdetail.UserDetailViewModel

class ViewModelFactory private constructor(private val dataRepository: DataRepository) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ListPostViewModel::class.java)) {
            return ListPostViewModel(dataRepository) as T
        } else if (modelClass.isAssignableFrom(DetailPostViewModel::class.java)) {
            return DetailPostViewModel(dataRepository) as T
        } else if (modelClass.isAssignableFrom(UserDetailViewModel::class.java)) {
            return UserDetailViewModel(dataRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null
        fun getInstance(): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(Injection.provideDataRepository())
            }.also { instance = it }
    }
}