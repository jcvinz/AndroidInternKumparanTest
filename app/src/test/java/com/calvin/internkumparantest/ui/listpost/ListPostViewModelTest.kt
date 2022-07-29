package com.calvin.internkumparantest.ui.listpost

import DataRepository
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.calvin.internkumparantest.ui.MainCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@Suppress("DEPRECATION")
@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class ListPostViewModelTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var dataRepository: DataRepository
    private lateinit var listPostViewModel: ListPostViewModel

    @Before
    fun setUp() {
        listPostViewModel = ListPostViewModel(dataRepository)
    }

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Test
    fun `when getting all user Should Call getAllUser from DataRepository`() = mainCoroutineRule.runBlockingTest {
        listPostViewModel.getAllUser()
        Mockito.verify(dataRepository).getAllUser()
    }

    @Test
    fun `when getting all post Should Call getAllPost from DataRepository`() = mainCoroutineRule.runBlockingTest {
        listPostViewModel.getAllPost()
        Mockito.verify(dataRepository).getAllPost()
    }
}