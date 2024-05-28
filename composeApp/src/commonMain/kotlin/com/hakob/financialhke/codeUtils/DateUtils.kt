package com.hakob.financialhke.codeUtils

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime

class DateUtils {
}

fun LocalDate.toLocalDateTimeTowardsEod(): LocalDateTime {
    return LocalDateTime(this, LocalTime(23, 59, 59, 59))
}

fun LocalDate.toLocalDateTimeTowardsSod(): LocalDateTime {
    return LocalDateTime(this, LocalTime(0, 0, 0, 0))
}