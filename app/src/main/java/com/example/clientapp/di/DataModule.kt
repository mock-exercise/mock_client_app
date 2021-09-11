package com.example.clientapp.di

import android.content.Context
import com.example.clientapp.model.remotesource.AuthService
import com.example.clientapp.model.repository.AuthRepository
import com.example.connectorlibrary.controller.ServiceControllerUser
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    @Singleton
    fun provideServiceController(@ApplicationContext context: Context): ServiceControllerUser =
        ServiceControllerUser(context)

    @Provides
    @Singleton
    fun provideAuthService(serviceControllerUser: ServiceControllerUser) = AuthService(serviceControllerUser)

//    @Provides
//    @Singleton
//    fun provideRemoteDatasource(serviceControllerUser: ServiceControllerUser) = MainService(serviceControllerUser)

    @Provides
    @Singleton
    fun provideAuthRepository(service : AuthService) = AuthRepository(service)


}