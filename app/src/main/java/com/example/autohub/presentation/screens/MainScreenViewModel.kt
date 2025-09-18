package com.example.autohub.presentation.screens

import androidx.lifecycle.ViewModel
import com.example.autohub.presentation.navigation.Navigator
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * ViewModel для [MainScreen].
 *
 * @property navigator Навигатор, который управляет переходами между экранами приложения.
 */
@HiltViewModel
class MainScreenViewModel @Inject constructor(
    val navigator: Navigator
) : ViewModel()