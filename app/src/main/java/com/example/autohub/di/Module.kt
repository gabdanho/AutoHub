//package com.example.autohub.di
//
//import com.example.autohub.notifications.FcmApi
//import com.example.autohub.notifications.NotificationBody
//import com.example.autohub.notifications.SendMessageDto
//import dagger.Module
//import dagger.Provides
//import dagger.hilt.InstallIn
//import dagger.hilt.android.components.ViewModelComponent
//import retrofit2.Retrofit
//import retrofit2.converter.moshi.MoshiConverterFactory
//import retrofit2.create
//import javax.inject.Singleton
//
//@Module
//@InstallIn(ViewModelComponent::class)
//object Module {
//
//    @Provides
//    @Singleton
//    fun provideFcmApi(): FcmApi {
//        return Retrofit.Builder()
//            .baseUrl("http://10.0.2.2:8080/")
//            .addConverterFactory(MoshiConverterFactory.create())
//            .build()
//            .create()
//    }
//}