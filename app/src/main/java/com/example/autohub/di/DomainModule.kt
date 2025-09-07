package com.example.autohub.di

import com.example.autohub.domain.interfaces.repository.local.UserPreferencesRepository
import com.example.autohub.domain.interfaces.repository.remote.AdDataRepository
import com.example.autohub.domain.interfaces.repository.remote.AuthUserRepository
import com.example.autohub.domain.interfaces.repository.remote.MessengerRepository
import com.example.autohub.domain.interfaces.repository.remote.UserDataRepository
import com.example.autohub.domain.interfaces.usecase.ChangePasswordUseCase
import com.example.autohub.domain.interfaces.usecase.ChangeUserStatusUseCase
import com.example.autohub.domain.interfaces.usecase.ClearUserIdUseCase
import com.example.autohub.domain.interfaces.usecase.CreateAdUseCase
import com.example.autohub.domain.interfaces.usecase.ForgotPasswordUseCase
import com.example.autohub.domain.interfaces.usecase.GetAdsUseCase
import com.example.autohub.domain.interfaces.usecase.GetAuthUserIdUseCase
import com.example.autohub.domain.interfaces.usecase.GetBuyerStatusUseCase
import com.example.autohub.domain.interfaces.usecase.GetBuyersChatsUseCase
import com.example.autohub.domain.interfaces.usecase.GetCountUnreadMessagesUseCase
import com.example.autohub.domain.interfaces.usecase.GetCurrentUserAdsUseCase
import com.example.autohub.domain.interfaces.usecase.GetMessagesUseCase
import com.example.autohub.domain.interfaces.usecase.GetLocalUserIdUseCase
import com.example.autohub.domain.interfaces.usecase.GetUserDataUseCase
import com.example.autohub.domain.interfaces.usecase.GetUserTokenUseCase
import com.example.autohub.domain.interfaces.usecase.InsertLocalUserIdUseCase
import com.example.autohub.domain.interfaces.usecase.LoginAndSaveUserIdUseCase
import com.example.autohub.domain.interfaces.usecase.LoginUserUseCase
import com.example.autohub.domain.interfaces.usecase.MarkMessagesAsReadUseCase
import com.example.autohub.domain.interfaces.usecase.RegisterAndSaveUserIdUseCase
import com.example.autohub.domain.interfaces.usecase.RegisterUserUseCase
import com.example.autohub.domain.interfaces.usecase.ResendEmailVerificationUseCase
import com.example.autohub.domain.interfaces.usecase.SendMessageUseCase
import com.example.autohub.domain.interfaces.usecase.SignOutAndClearUserIdUseCase
import com.example.autohub.domain.interfaces.usecase.SignOutUseCase
import com.example.autohub.domain.interfaces.usecase.UpdateCityUseCase
import com.example.autohub.domain.interfaces.usecase.UpdateFirstNameUseCase
import com.example.autohub.domain.interfaces.usecase.UpdateLastNameUseCase
import com.example.autohub.domain.interfaces.usecase.UploadAdsImagesToFirebaseUseCase
import com.example.autohub.domain.interfaces.usecase.UploadUserProfileImageToFirebaseUseCase
import com.example.autohub.domain.usecase.ChangePasswordUseCaseImpl
import com.example.autohub.domain.usecase.ChangeUserStatusUseCaseImpl
import com.example.autohub.domain.usecase.ClearUserIdUseCaseImpl
import com.example.autohub.domain.usecase.CreateAdUseCaseImpl
import com.example.autohub.domain.usecase.ForgotPasswordUseCaseImpl
import com.example.autohub.domain.usecase.GetAdsUseCaseImpl
import com.example.autohub.domain.usecase.GetAuthUserIdUseCaseImpl
import com.example.autohub.domain.usecase.GetBuyerStatusUseCaseImpl
import com.example.autohub.domain.usecase.GetBuyersChatsUseCaseImpl
import com.example.autohub.domain.usecase.GetCountUnreadMessagesUseCaseImpl
import com.example.autohub.domain.usecase.GetCurrentUserAdsUseCaseImpl
import com.example.autohub.domain.usecase.GetMessagesUseCaseImpl
import com.example.autohub.domain.usecase.GetLocalUserIdUseCaseImpl
import com.example.autohub.domain.usecase.GetUserDataUseCaseImpl
import com.example.autohub.domain.usecase.GetUserTokenUseCaseImpl
import com.example.autohub.domain.usecase.InsertLocalUserIdUseCaseImpl
import com.example.autohub.domain.usecase.LoginAndSaveUserIdUseCaseImpl
import com.example.autohub.domain.usecase.LoginUserUseCaseImpl
import com.example.autohub.domain.usecase.MarkMessagesAsReadUseCaseImpl
import com.example.autohub.domain.usecase.RegisterAndSaveUserIdUseCaseImpl
import com.example.autohub.domain.usecase.RegisterUserUseCaseImpl
import com.example.autohub.domain.usecase.ResendEmailVerificationUseCaseImpl
import com.example.autohub.domain.usecase.SendMessageUseCaseImpl
import com.example.autohub.domain.usecase.SignOutAndClearUserIdUseCaseImpl
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
import kotlin.math.log

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
    fun provideGetAdsUseCase(adDataRepository: AdDataRepository): GetAdsUseCase {
        return GetAdsUseCaseImpl(adDataRepository = adDataRepository)
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
    fun provideInsertLocalUserIdUseCase(
        userPreferences: UserPreferencesRepository
    ): InsertLocalUserIdUseCase {
        return InsertLocalUserIdUseCaseImpl(userPreferences = userPreferences)
    }

    @Provides
    @Singleton
    fun provideGetLocalUserIdUseCase(userPreferences: UserPreferencesRepository): GetLocalUserIdUseCase {
        return GetLocalUserIdUseCaseImpl(userPreferences = userPreferences)
    }

    @Provides
    @Singleton
    fun provideGetAuthUserIdUseCase(authUserRepository: AuthUserRepository): GetAuthUserIdUseCase {
        return GetAuthUserIdUseCaseImpl(authUserRepository = authUserRepository)
    }

    @Provides
    @Singleton
    fun provideLoginAndSaveUserIdUseCase(
        loginUserUseCase: LoginUserUseCase,
        getUserIdUseCase: GetAuthUserIdUseCase,
        insertLocalUserIdUseCase: InsertLocalUserIdUseCase
    ): LoginAndSaveUserIdUseCase {
        return LoginAndSaveUserIdUseCaseImpl(
            loginUserUseCase = loginUserUseCase,
            getUserIdUseCase = getUserIdUseCase,
            insertLocalUserIdUseCase = insertLocalUserIdUseCase
        )
    }

    @Provides
    @Singleton
    fun provideRegisterAndSaveUserIdUseCase(
        registerUserUseCase: RegisterUserUseCase,
        getUserIdUseCase: GetAuthUserIdUseCase,
        insertLocalUserIdUseCase: InsertLocalUserIdUseCase
    ): RegisterAndSaveUserIdUseCase {
        return RegisterAndSaveUserIdUseCaseImpl(
            registerUserUseCase = registerUserUseCase,
            getUserIdUseCase = getUserIdUseCase,
            insertLocalUserIdUseCase = insertLocalUserIdUseCase
        )
    }

    @Provides
    @Singleton
    fun provideClearUserUidUseCase(userPreferences: UserPreferencesRepository): ClearUserIdUseCase {
        return ClearUserIdUseCaseImpl(userPreferences = userPreferences)
    }

    @Provides
    @Singleton
    fun provideSignOutAndClearUserIdUseCase(
        signOutUseCase: SignOutUseCase,
        clearUserIdUseCase: ClearUserIdUseCase
    ): SignOutAndClearUserIdUseCase {
        return SignOutAndClearUserIdUseCaseImpl(
            signOutUseCase = signOutUseCase,
            clearUserIdUseCase = clearUserIdUseCase
        )
    }

    @Provides
    @Singleton
    fun provideResendEmailVerificationUseCase(authUserRepository: AuthUserRepository): ResendEmailVerificationUseCase {
        return ResendEmailVerificationUseCaseImpl(authUserRepository = authUserRepository)
    }

    @Provides
    @Singleton
    fun provideForgotPasswordUseCase(authUserRepository: AuthUserRepository): ForgotPasswordUseCase {
        return ForgotPasswordUseCaseImpl(authUserRepository = authUserRepository)
    }
}