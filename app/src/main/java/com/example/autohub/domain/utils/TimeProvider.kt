package com.example.autohub.domain.utils

interface TimeProvider {
    fun currentTimeMillis(): Long
}