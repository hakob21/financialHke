package com.hakob.financialhke.utils

import app.cash.sqldelight.db.SqlDriver

internal expect fun sqlDriverForTesting(): SqlDriver
