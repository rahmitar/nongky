package com.noobee.nongky.di

import com.google.gson.GsonBuilder
import com.noobee.nongky.BuildConfig
import com.noobee.nongky.util.Constant
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    fun providesBaseUrl() = Constant.BASE_URL

    @Singleton
    @Provides
    fun providesOkHttpClient(): OkHttpClient {
        if (BuildConfig.DEBUG){
            val interceptor = HttpLoggingInterceptor()
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            return OkHttpClient.Builder().addInterceptor(interceptor).build()
        } else {
            return OkHttpClient.Builder().retryOnConnectionFailure(true).build()
        }
    }

    @Singleton
    @Provides
    fun providesRetrofit(okHttpClient: OkHttpClient, baseUrl: String): Retrofit{
        val gson = GsonBuilder().setLenient().create()
        return Retrofit.Builder().addConverterFactory(GsonConverterFactory.create(gson)).baseUrl(baseUrl).client(okHttpClient).build()
    }
}