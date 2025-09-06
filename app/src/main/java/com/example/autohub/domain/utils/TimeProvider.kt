package com.example.autohub.domain.utils

interface TimeProvider {
    fun currentTimeMillis(): Long

    fun millisToDate(millis: Long): String
}