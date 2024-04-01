package com.hakob.financialhke

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform