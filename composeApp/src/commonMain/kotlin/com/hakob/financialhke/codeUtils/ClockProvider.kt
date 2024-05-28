package com.hakob.financialhke.codeUtils

import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

interface ClockProvider {
    fun getCurrentLocalDateTime(): LocalDateTime
}

class ActualClockProvider: ClockProvider {
    override fun getCurrentLocalDateTime(): LocalDateTime {
        return Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
    }
}