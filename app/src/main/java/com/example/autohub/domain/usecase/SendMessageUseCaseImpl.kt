package com.example.autohub.domain.usecase

import com.example.autohub.data.firebase.model.safeFirebaseCall
import com.example.autohub.domain.interfaces.repository.remote.MessengerRepository
import com.example.autohub.domain.interfaces.usecase.HasInternetConnectionUseCase
import com.example.autohub.domain.interfaces.usecase.SendMessageUseCase
import com.example.autohub.domain.model.result.FirebaseResult
import com.example.autohub.domain.model.result.HandleErrorTag
import com.example.autohub.domain.model.user.User
import kotlinx.coroutines.flow.first

class SendMessageUseCaseImpl(
    private val messengerRepository: MessengerRepository,
    private val hasInternetConnectionUseCase: HasInternetConnectionUseCase,
) : SendMessageUseCase {

    override suspend fun invoke(sender: User, receiver: User, text: String): FirebaseResult<Unit> {
        val isOnline = hasInternetConnectionUseCase().first()

        if (!isOnline) return FirebaseResult.Error.HandledError(tag = HandleErrorTag.NO_INTERNET)

        return safeFirebaseCall {
            messengerRepository.sendMessage(
                sender = sender,
                receiver = receiver,
                text = text
            )
        }
    }
}