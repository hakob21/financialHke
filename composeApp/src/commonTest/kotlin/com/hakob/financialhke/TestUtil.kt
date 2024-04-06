package com.hakob.financialhke

import app.cash.sqldelight.db.SqlDriver

internal expect fun sqlDriverForTesting(): SqlDriver
