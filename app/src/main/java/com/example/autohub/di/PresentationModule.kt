package com.example.autohub.di

import com.example.autohub.presentation.navigation.Navigator
import com.example.autohub.presentation.navigation.NavigatorImpl
import com.example.autohub.presentation.navigation.model.graphs.NavigationGraph
import com.example.autohub.presentation.navigation.model.graphs.destinations.AdGraph
import com.example.autohub.presentation.navigation.model.graphs.destinations.AuthGraph
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PresentationModule {

    @Singleton
    @Provides
    fun provideNavigator(): Navigator {
        return NavigatorImpl(startDestination = NavigationGraph.Auth)
    }
}