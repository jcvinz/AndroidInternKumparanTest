import com.calvin.internkumparantest.data.*
import retrofit2.Response

class DataRepository private constructor(private val apiService: ApiService) {

    suspend fun getAllUser(): Response<List<UserResponseItem>> = apiService.getAllUser()
    suspend fun getAllPost(): Response<List<PostResponseItem>> = apiService.getAllPost()
    suspend fun getComments(postId: Int): Response<List<CommentResponseItem>> =
        apiService.getComments(postId)

    suspend fun getAlbums(userId: Int): Response<List<AlbumResponseItem>> =
        apiService.getAlbums(userId)

    suspend fun getPhotos(albumId: Int): Response<List<PhotoResponseItem>> =
        apiService.getPhotos(albumId)

    suspend fun getUserDetail(userId: Int): Response<List<UserResponseItem>> =
        apiService.getUserDetail(userId)

    companion object {
        @Volatile
        private var instance: DataRepository? = null
        fun getInstance(apiService: ApiService): DataRepository = instance ?: synchronized(this) {
            instance ?: DataRepository(apiService)
        }.also { instance = it }
    }
}