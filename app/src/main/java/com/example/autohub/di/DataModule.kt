package com.example.autohub.di

import android.content.Context
import com.example.autohub.data.firebase.utils.FirebaseStorageUtils
import com.example.autohub.data.local.dao.TokenRepositoryDao
import com.example.autohub.data.local.database.TokenRepositoryDatabase
import com.example.autohub.data.repository.impl.firebase.AdDataRepositoryImpl
import com.example.autohub.data.repository.impl.firebase.AuthUserRepositoryImpl
import com.example.autohub.data.repository.impl.firebase.MessengerRepositoryImpl
import com.example.autohub.data.repository.impl.firebase.UserDataRepositoryImpl
import com.example.autohub.data.repository.impl.room.TokenRepositoryImpl
import com.example.autohub.data.time.SystemTimeProvider
import com.example.autohub.domain.interfaces.repository.firebase.AdDataRepository
import com.example.autohub.domain.interfaces.repository.firebase.AuthUserRepository
import com.example.autohub.domain.interfaces.repository.firebase.MessengerRepository
import com.example.autohub.domain.interfaces.repository.firebase.UserDataRepository
import com.example.autohub.domain.interfaces.repository.room.TokenRepository
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
    fun provideTokenDatabase(@ApplicationContext context: Context): TokenRepositoryDatabase {
        return TokenRepositoryDatabase.getDatabase(context = context)
    }

    @Provides
    @Singleton
    fun provideTokenRepositoryDao(database: TokenRepositoryDatabase): TokenRepositoryDao {
        return database.tokenDao()
    }

    @Provides
    @Singleton
    fun provideTokenRepository(): TokenRepository {
        return TokenRepositoryImpl(
            tokenRepositoryDao = TODO()
        )
    }
}