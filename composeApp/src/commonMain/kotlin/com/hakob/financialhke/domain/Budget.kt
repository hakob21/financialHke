package com.hakob.financialhke.domain

import kotlinx.datetime.LocalDate

data class Budget(
    val sum: Double,
    val localDate: LocalDate,
)