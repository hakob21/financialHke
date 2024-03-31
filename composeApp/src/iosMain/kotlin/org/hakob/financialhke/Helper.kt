package org.hakob.financialhke

import koin.coreModule
import koin.platformModule
import org.koin.core.context.startKoin

fun initKoin() {
    // start Koin
    startKoin {
        modules(coreModule() + platformModule)
    }
}