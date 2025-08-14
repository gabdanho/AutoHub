package com.example.autohub.presentation.screens

import androidx.lifecycle.ViewModel
import com.example.autohub.presentation.navigation.Navigator
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    val navigator: Navigator
) : ViewModel()