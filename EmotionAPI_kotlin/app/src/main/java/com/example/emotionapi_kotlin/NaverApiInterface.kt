package com.example.emotionapi_kotlin

import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part


interface NaverApiInterface {

    @Multipart
    @POST("/v1/vision/celebrity")
    fun naverRepo(
        @Header("X-Naver-Client-Id") id: String?,
        @Header("X-Naver-Client-Secret") secret: String?,
        @Part file: MultipartBody.Part?,
    ): Call<NaverRepo>

    @Multipart
    @POST("/v1/vision/face")
    fun naverRepo2(
        @Header("X-Naver-Client-Id") id: String?,
        @Header("X-Naver-Client-Secret") secret: String?,
        @Part file: MultipartBody.Part?,
    ): Call<NaverRepo>
}