package com.dicoding.picodiploma.loginwithanimation.data.remote.retrofit

import com.dicoding.picodiploma.loginwithanimation.data.remote.pref.LoginResponse
import com.dicoding.picodiploma.loginwithanimation.data.remote.pref.SignupResponse
import com.dicoding.picodiploma.loginwithanimation.data.remote.pref.StoriesResponse
import com.dicoding.picodiploma.loginwithanimation.data.remote.pref.StoryUploadResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @FormUrlEncoded
    @POST("register")
    suspend fun register(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): SignupResponse

    @FormUrlEncoded
    @POST("login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): LoginResponse

    @Multipart
    @POST("stories")
    suspend fun uploadStory(
        @Part file: MultipartBody.Part,
        @Part("description") description: RequestBody,
    ): StoryUploadResponse

    @GET("stories")
    suspend fun getAllStories(
        @Query("page") page: Int = 1,
        @Query("size") size: Int = 20
    ): StoriesResponse

    @GET("stories/{id}")
    suspend fun getStoryDetail(@Path("id") id: String): StoriesResponse

    @GET("stories")
    suspend fun getStoriesWithLocation(
        @Query("location") location : Int = 1,
    ): StoriesResponse
}