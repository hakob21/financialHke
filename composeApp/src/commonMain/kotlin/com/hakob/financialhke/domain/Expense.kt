package com.hakob.financialhke.domain

import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

data class Expense(
    val sum: Double,
    val localDate: LocalDate = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
)