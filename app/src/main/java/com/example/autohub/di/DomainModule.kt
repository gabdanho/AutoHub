package com.example.autohub.di

import com.example.autohub.domain.interfaces.repository.firebase.AdDataRepository
import com.example.autohub.domain.interfaces.repository.firebase.AuthUserRepository
import com.example.autohub.domain.interfaces.repository.firebase.MessengerRepository
import com.example.autohub.domain.interfaces.repository.firebase.UserDataRepository
import com.example.autohub.domain.interfaces.repository.room.TokenRepository
import com.example.autohub.domain.interfaces.usecase.ChangePasswordUseCase
import com.example.autohub.domain.interfaces.usecase.ChangeUserStatusUseCase
import com.example.autohub.domain.interfaces.usecase.CreateAdUseCase
import com.example.autohub.domain.interfaces.usecase.GetAdsBySearchTextAndFiltersUseCase
import com.example.autohub.domain.interfaces.usecase.GetAdsWithFiltersUseCase
import com.example.autohub.domain.interfaces.usecase.GetBuyerStatusUseCase
import com.example.autohub.domain.interfaces.usecase.GetBuyersChatsUseCase
import com.example.autohub.domain.interfaces.usecase.GetCountUnreadMessagesUseCase
import com.example.autohub.domain.interfaces.usecase.GetCurrentUserAdsUseCase
import com.example.autohub.domain.interfaces.usecase.GetMessagesUseCase
import com.example.autohub.domain.interfaces.usecase.GetTokenFromDatabaseUseCase
import com.example.autohub.domain.interfaces.usecase.GetUserDataUseCase
import com.example.autohub.domain.interfaces.usecase.GetUserTokenUseCase
import com.example.autohub.domain.interfaces.usecase.InsertTokenUseCase
import com.example.autohub.domain.interfaces.usecase.LoginUserUseCase
import com.example.autohub.domain.interfaces.usecase.MarkMessagesAsReadUseCase
import com.example.autohub.domain.interfaces.usecase.RegisterUserUseCase
import com.example.autohub.domain.interfaces.usecase.SendMessageUseCase
import com.example.autohub.domain.interfaces.usecase.SignOutUseCase
import com.example.autohub.domain.interfaces.usecase.UpdateCityUseCase
import com.example.autohub.domain.interfaces.usecase.UpdateFirstNameUseCase
import com.example.autohub.domain.interfaces.usecase.UpdateLastNameUseCase
import com.example.autohub.domain.interfaces.usecase.UploadAdsImagesToFirebaseUseCase
import com.example.autohub.domain.interfaces.usecase.UploadUserProfileImageToFirebaseUseCase
import com.example.autohub.domain.usecase.ChangePasswordUseCaseImpl
import com.example.autohub.domain.usecase.ChangeUserStatusUseCaseImpl
import com.example.autohub.domain.usecase.CreateAdUseCaseImpl
import com.example.autohub.domain.usecase.GetAdsBySearchTextAndFiltersUseCaseImpl
import com.example.autohub.domain.usecase.GetAdsWithFiltersUseCaseImpl
import com.example.autohub.domain.usecase.GetBuyerStatusUseCaseImpl
import com.example.autohub.domain.usecase.GetBuyersChatsUseCaseImpl
import com.example.autohub.domain.usecase.GetCountUnreadMessagesUseCaseImpl
import com.example.autohub.domain.usecase.GetCurrentUserAdsUseCaseImpl
import com.example.autohub.domain.usecase.GetMessagesUseCaseImpl
import com.example.autohub.domain.usecase.GetTokenFromDatabaseUseCaseImpl
import com.example.autohub.domain.usecase.GetUserDataUseCaseImpl
import com.example.autohub.domain.usecase.GetUserTokenUseCaseImpl
import com.example.autohub.domain.usecase.InsertTokenUseCaseImpl
import com.example.autohub.domain.usecase.LoginUserUseCaseImpl
import com.example.autohub.domain.usecase.MarkMessagesAsReadUseCaseImpl
import com.example.autohub.domain.usecase.RegisterUserUseCaseImpl
import com.example.autohub.domain.usecase.SendMessageUseCaseImpl
import com.example.autohub.domain.usecase.SignOutUseCaseImpl
import com.example.autohub.domain.usecase.UpdateCityUseCaseImpl
import com.example.autohub.domain.usecase.UpdateFirstNameUseCaseImpl
import com.example.autohub.domain.usecase.UpdateLastNameUseCaseImpl
import com.example.autohub.domain.usecase.UploadAdsImagesToFirebaseUseCaseImpl
import com.example.autohub.domain.usecase.UploadUserProfileImageToFirebaseUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DomainModule {

    @Provides
    @Singleton
    fun provideChangeUserStatusUseCase(authUserRepository: AuthUserRepository): ChangeUserStatusUseCase {
        return ChangeUserStatusUseCaseImpl(authUserRepository = authUserRepository)
    }

    @Provides
    @Singleton
    fun provideRegisterUserUseCase(authUserRepository: AuthUserRepository): RegisterUserUseCase {
        return RegisterUserUseCaseImpl(authUserRepository = authUserRepository)
    }

    @Provides
    @Singleton
    fun provideLoginUserUseCase(authUserRepository: AuthUserRepository): LoginUserUseCase {
        return LoginUserUseCaseImpl(authUserRepository = authUserRepository)
    }

    @Provides
    @Singleton
    fun provideGetUserTokenUseCase(authUserRepository: AuthUserRepository): GetUserTokenUseCase {
        return GetUserTokenUseCaseImpl(authUserRepository = authUserRepository)
    }

    @Provides
    @Singleton
    fun provideSignOutUseCase(authUserRepository: AuthUserRepository): SignOutUseCase {
        return SignOutUseCaseImpl(authUserRepository = authUserRepository)
    }

    @Provides
    @Singleton
    fun provideChangePasswordUseCase(authUserRepository: AuthUserRepository): ChangePasswordUseCase {
        return ChangePasswordUseCaseImpl(authUserRepository = authUserRepository)
    }

    @Provides
    @Singleton
    fun provideCreateAdUseCase(adDataRepository: AdDataRepository): CreateAdUseCase {
        return CreateAdUseCaseImpl(adDataRepository = adDataRepository)
    }

    @Provides
    @Singleton
    fun provideGetAdsBySearchTextAndFiltersUseCase(adDataRepository: AdDataRepository): GetAdsBySearchTextAndFiltersUseCase {
        return GetAdsBySearchTextAndFiltersUseCaseImpl(adDataRepository = adDataRepository)
    }

    @Provides
    @Singleton
    fun provideGetAdsWithFiltersUseCase(adDataRepository: AdDataRepository): GetAdsWithFiltersUseCase {
        return GetAdsWithFiltersUseCaseImpl(adDataRepository = adDataRepository)
    }

    @Provides
    @Singleton
    fun provideGetCurrentUserAdsUseCase(adDataRepository: AdDataRepository): GetCurrentUserAdsUseCase {
        return GetCurrentUserAdsUseCaseImpl(adDataRepository = adDataRepository)
    }

    @Provides
    @Singleton
    fun provideUploadAdsImagesToFirebaseUseCase(adDataRepository: AdDataRepository): UploadAdsImagesToFirebaseUseCase {
        return UploadAdsImagesToFirebaseUseCaseImpl(adDataRepository = adDataRepository)
    }

    @Provides
    @Singleton
    fun provideGetBuyersChatsUseCase(messengerRepository: MessengerRepository): GetBuyersChatsUseCase {
        return GetBuyersChatsUseCaseImpl(messengerRepository = messengerRepository)
    }

    @Provides
    @Singleton
    fun provideGetBuyerStatusUseCase(messengerRepository: MessengerRepository): GetBuyerStatusUseCase {
        return GetBuyerStatusUseCaseImpl(messengerRepository = messengerRepository)
    }

    @Provides
    @Singleton
    fun provideGetCountUnreadMessagesUseCase(messengerRepository: MessengerRepository): GetCountUnreadMessagesUseCase {
        return GetCountUnreadMessagesUseCaseImpl(messengerRepository = messengerRepository)
    }

    @Provides
    @Singleton
    fun provideGetMessagesUseCase(messengerRepository: MessengerRepository): GetMessagesUseCase {
        return GetMessagesUseCaseImpl(messengerRepository = messengerRepository)
    }

    @Provides
    @Singleton
    fun provideMarkMessagesAsReadUseCase(messengerRepository: MessengerRepository): MarkMessagesAsReadUseCase {
        return MarkMessagesAsReadUseCaseImpl(messengerRepository = messengerRepository)
    }

    @Provides
    @Singleton
    fun provideSendMessageUseCase(messengerRepository: MessengerRepository): SendMessageUseCase {
        return SendMessageUseCaseImpl(messengerRepository = messengerRepository)
    }

    @Provides
    @Singleton
    fun provideGetUserDataUseCase(userDataRepository: UserDataRepository): GetUserDataUseCase {
        return GetUserDataUseCaseImpl(userDataRepository = userDataRepository)
    }

    @Provides
    @Singleton
    fun provideUpdateCityUseCase(userDataRepository: UserDataRepository): UpdateCityUseCase {
        return UpdateCityUseCaseImpl(userDataRepository = userDataRepository)
    }

    @Provides
    @Singleton
    fun provideUpdateFirstNameUseCase(userDataRepository: UserDataRepository): UpdateFirstNameUseCase {
        return UpdateFirstNameUseCaseImpl(userDataRepository = userDataRepository)
    }
    @Provides
    @Singleton
    fun provideUpdateLastNameUseCase(userDataRepository: UserDataRepository): UpdateLastNameUseCase {
        return UpdateLastNameUseCaseImpl(userDataRepository = userDataRepository)
    }


    @Provides
    @Singleton
    fun provideUploadUserProfileImageToFirebaseUseCase(userDataRepository: UserDataRepository): UploadUserProfileImageToFirebaseUseCase {
        return UploadUserProfileImageToFirebaseUseCaseImpl(userDataRepository = userDataRepository)
    }

    @Provides
    @Singleton
    fun provideInsertTokenUseCase(
        tokenRepository: TokenRepository,
        authUserRepository: AuthUserRepository
    ): InsertTokenUseCase {
        return InsertTokenUseCaseImpl(
            tokenRepository = tokenRepository,
            authUserRepository = authUserRepository
        )
    }

    @Provides
    @Singleton
    fun provideGetTokenFromDatabaseUseCase(tokenRepository: TokenRepository): GetTokenFromDatabaseUseCase {
        return GetTokenFromDatabaseUseCaseImpl(tokenRepository = tokenRepository)
    }
}