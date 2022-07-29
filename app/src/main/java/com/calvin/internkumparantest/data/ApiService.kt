package com.calvin.internkumparantest.data

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("users")
    suspend fun getAllUser(): Response<List<UserResponseItem>>

    @GET("posts")
    suspend fun getAllPost(): Response<List<PostResponseItem>>

    @GET("comments")
    suspend fun getComments(@Query("postId") postId: Int): Response<List<CommentResponseItem>>

    @GET("albums")
    suspend fun getAlbums(@Query("userId") userId: Int): Response<List<AlbumResponseItem>>

    @GET("photos")
    suspend fun getPhotos(@Query("albumId") albumId: Int): Response<List<PhotoResponseItem>>

    @GET("users")
    suspend fun getUserDetail(@Query("userId") userId: Int): Response<List<UserResponseItem>>
}