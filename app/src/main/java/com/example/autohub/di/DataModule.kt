package com.example.autohub.di

import android.content.Context
import com.example.autohub.data.firebase.utils.FirebaseStorageUtils
import com.example.autohub.data.local.datasource.UserSharedPreferences
import com.example.autohub.data.repository.impl.firebase.AdDataRepositoryImpl
import com.example.autohub.data.repository.impl.firebase.AuthUserRepositoryImpl
import com.example.autohub.data.repository.impl.firebase.MessengerRepositoryImpl
import com.example.autohub.data.repository.impl.firebase.UserDataRepositoryImpl
import com.example.autohub.data.utils.NetworkRepositoryImpl
import com.example.autohub.data.utils.SystemTimeProvider
import com.example.autohub.domain.interfaces.repository.local.NetworkRepository
import com.example.autohub.domain.interfaces.repository.local.UserDataSource
import com.example.autohub.domain.interfaces.repository.remote.AdDataRepository
import com.example.autohub.domain.interfaces.repository.remote.AuthUserRepository
import com.example.autohub.domain.interfaces.repository.remote.MessengerRepository
import com.example.autohub.domain.interfaces.repository.remote.UserDataRepository
import com.example.autohub.domain.interfaces.usecase.MillisToDateUseCase
import com.example.autohub.domain.interfaces.usecase.MillisToTimeUseCase
import com.example.autohub.domain.usecase.MillisToDateUseCaseImpl
import com.example.autohub.domain.usecase.MillisToTimeUseCaseImpl
import com.example.autohub.domain.utils.TimeProvider
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.messaging
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.storage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Модуль для предоставления зависимостей уровня Data
 */
@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideFirebaseFirestore(): FirebaseFirestore {
        return Firebase.firestore
    }

    @Provides
    @Singleton
    fun provideFirebaseStorage(): FirebaseStorage {
        return Firebase.storage
    }

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth {
        return Firebase.auth
    }

    @Provides
    @Singleton
    fun provideFirebaseMessaging(): FirebaseMessaging {
        return Firebase.messaging
    }

    @Provides
    @Singleton
    fun provideTimeProvider(): TimeProvider {
        return SystemTimeProvider()
    }

    @Provides
    @Singleton
    fun provideFirebaseStorageUtils(fbStorage: FirebaseStorage): FirebaseStorageUtils {
        return FirebaseStorageUtils(fbStorage = fbStorage)
    }

    @Provides
    @Singleton
    fun provideAdDataRepository(
        fbFirestore: FirebaseFirestore,
        timeProvider: TimeProvider,
        fbStorageUtils: FirebaseStorageUtils
    ): AdDataRepository {
        return AdDataRepositoryImpl(
            fbFirestore = fbFirestore,
            fbStorageUtils = fbStorageUtils,
            timeProvider = timeProvider
        )
    }

    @Provides
    @Singleton
    fun provideAuthUserRepository(
        fbAuth: FirebaseAuth,
        fbStore: FirebaseFirestore,
        fbMessaging: FirebaseMessaging
    ): AuthUserRepository {
        return AuthUserRepositoryImpl(
            fbAuth = fbAuth,
            fbStore = fbStore,
            fbMessaging = fbMessaging
        )
    }

    @Provides
    @Singleton
    fun provideMessengerRepository(
        fbFirestore: FirebaseFirestore,
        timeProvider: TimeProvider
    ): MessengerRepository {
        return MessengerRepositoryImpl(
            fbFirestore = fbFirestore,
            timeProvider = timeProvider
        )
    }

    @Provides
    @Singleton
    fun provideUserDataRepository(
        fbFirestore: FirebaseFirestore,
        fbAuth: FirebaseAuth,
        fbStorageUtils: FirebaseStorageUtils
    ): UserDataRepository {
        return UserDataRepositoryImpl(
            fbFirestore = fbFirestore,
            fbAuth = fbAuth,
            fbStorageUtils = fbStorageUtils
        )
    }

    @Provides
    @Singleton
    fun provideUserDataSource(@ApplicationContext context: Context): UserDataSource {
        return UserSharedPreferences(context = context)
    }

    @Provides
    @Singleton
    fun provideNetworkRepository(@ApplicationContext context: Context): NetworkRepository {
        return NetworkRepositoryImpl(context = context)
    }

    @Provides
    @Singleton
    fun provideMillisToDate(systemTimeProvider: TimeProvider): MillisToDateUseCase {
        return MillisToDateUseCaseImpl(systemTimeProvider = systemTimeProvider)
    }

    @Provides
    @Singleton
    fun provideMillisToTime(systemTimeProvider: TimeProvider): MillisToTimeUseCase {
        return MillisToTimeUseCaseImpl(systemTimeProvider = systemTimeProvider)
    }
}