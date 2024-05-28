package com.hakob.financialhke.utils.annotation.testCodeUtils

import com.hakob.financialhke.codeUtils.ClockProvider
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.Month

class AlwaysJuneFirstClockProvider: ClockProvider{
    override fun getCurrentLocalDateTime(): LocalDateTime {
        return LocalDateTime(LocalDate(2024, Month.JUNE, 1), LocalTime(11, 11, 11))
    }
}