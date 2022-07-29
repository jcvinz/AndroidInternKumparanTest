package com.calvin.internkumparantest.ui.detailpost

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
class DetailPostViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var dataRepository: DataRepository
    private lateinit var detailPostViewModel: DetailPostViewModel

    @Before
    fun setUp() {
        detailPostViewModel = DetailPostViewModel(dataRepository)
    }

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Test
    fun `when getting DetailPost & Comments Should Call getComments with same postId from DataRepository`() =
        mainCoroutineRule.runBlockingTest {
            detailPostViewModel.getComments(1)
            Mockito.verify(dataRepository).getComments(1)
        }

}