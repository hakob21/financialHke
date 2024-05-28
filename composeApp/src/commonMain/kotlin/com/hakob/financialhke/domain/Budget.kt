package com.hakob.financialhke.domain

import kotlinx.datetime.LocalDateTime

data class Budget(
    val sum: Double,
    val localDate: LocalDateTime,
)