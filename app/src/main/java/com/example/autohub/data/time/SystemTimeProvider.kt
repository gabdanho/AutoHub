package com.example.autohub.data.time

import com.example.autohub.domain.utils.TimeProvider

class SystemTimeProvider : TimeProvider {

    override fun currentTimeMillis(): Long = System.currentTimeMillis()
}