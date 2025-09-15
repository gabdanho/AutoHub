package com.example.autohub.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.autohub.domain.interfaces.usecase.ChangeUserStatusUseCase
import com.example.autohub.presentation.mapper.toUserStatusDomain
import com.example.autohub.presentation.model.user.UserStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val changeUserStatusUseCase: ChangeUserStatusUseCase
) : ViewModel() {

    fun changeUserStatus(status: UserStatus) {
        viewModelScope.launch {
            changeUserStatusUseCase(status = status.toUserStatusDomain())
        }
    }
}