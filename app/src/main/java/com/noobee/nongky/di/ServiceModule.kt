package com.noobee.nongky.di

import com.noobee.nongky.api.CafeService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn (SingletonComponent::class)
object ServiceModule {

    @Provides
    @Singleton
    fun providesCafeService(retrofit: Retrofit) = retrofit.create(CafeService::class.java)

}