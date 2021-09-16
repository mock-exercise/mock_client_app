package com.example.clientapp.di

import android.content.Context
import com.example.clientapp.data.repository.localsource.DataStoreManager
import com.example.clientapp.data.repository.remotesource.AuthService
import com.example.clientapp.data.repository.AuthRepository
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
    fun provideUserPreference(@ApplicationContext context: Context) = DataStoreManager(context)

    @Provides
    @Singleton
    fun provideAuthRepository(service : AuthService, dataStoreManager: DataStoreManager) = AuthRepository(service, dataStoreManager)
}