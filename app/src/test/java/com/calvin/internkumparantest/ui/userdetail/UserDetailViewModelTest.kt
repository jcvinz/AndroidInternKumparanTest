package com.calvin.internkumparantest.ui.userdetail

import DataRepository
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.calvin.internkumparantest.ui.MainCoroutineRule
import com.calvin.internkumparantest.ui.listpost.ListPostViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.*
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
class UserDetailViewModelTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var dataRepository: DataRepository
    private lateinit var userDetailViewModel: UserDetailViewModel

    @Before
    fun setUp() {
        userDetailViewModel = UserDetailViewModel(dataRepository)
    }

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Test
    fun `when getting user detail Should call getUserDetail with userId param from DataRepository`() =
        mainCoroutineRule.runBlockingTest {
            userDetailViewModel.getUserDetail(1)
            Mockito.verify(dataRepository).getUserDetail(1)
        }

    @Test
    fun `when getting albums Should call getAlbums with userId param from DataRepository`() =
        mainCoroutineRule.runBlockingTest {
            userDetailViewModel.getAlbums(1)
            Mockito.verify(dataRepository).getAlbums(1)
        }

    @Test
    fun `when getting album photos Should call getPhotos with albumId param from DataRepository`() =
        mainCoroutineRule.runBlockingTest {
            userDetailViewModel.getPhotos(1)
            Mockito.verify(dataRepository).getPhotos(1)
        }
}