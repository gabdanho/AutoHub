package com.example.autohub.domain

import com.example.autohub.domain.model.HandleErrorTag

class HandledException(val tag: HandleErrorTag) : Exception()