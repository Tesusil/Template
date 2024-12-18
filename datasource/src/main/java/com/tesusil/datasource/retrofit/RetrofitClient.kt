package com.tesusil.datasource.retrofit

import androidx.core.os.BuildCompat
import com.tesusil.template.datasource.BuildConfig
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject


class RetrofitClient @Inject constructor(){
    fun client(): Retrofit {
        return Retrofit
            .Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}