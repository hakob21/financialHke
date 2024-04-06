package com.hakob.financialhke

import com.hakob.financialhke.koin.coreModule
import com.hakob.financialhke.koin.platformModule
import org.koin.core.context.startKoin

fun initKoin() {
    // start Koin
    startKoin {
        modules(coreModule() + platformModule)
    }
}