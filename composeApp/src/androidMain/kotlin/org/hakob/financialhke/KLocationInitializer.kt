package org.hakob.financialhke

import android.content.Context
import androidx.startup.Initializer

internal lateinit var applicationContext: Context

public class KLocationInitializer: Initializer<KLocationContext> {
    override fun create(context: Context): KLocationContext {
        applicationContext = context.applicationContext
        return KLocationContext
    }

    override fun dependencies(): List<Class<out Initializer<*>>> {
        return emptyList()
    }
}