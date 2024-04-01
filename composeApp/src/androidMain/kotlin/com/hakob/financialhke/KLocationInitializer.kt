package com.hakob.financialhke

import android.content.Context
import androidx.startup.Initializer

internal lateinit var applicationContext: Context

// this call is needed for the androidx.startup:startup-runtime:1.1.0 library, which initializes
// providers (classes) at application startup. In this case we kind of hack it I guess and use it
// to have a variable applicationContext readily available from all the android platform specific code
// more specifically we need it for SqlDelight library (SqlDriver initialization) https://github.com/hakob21/financialHke/blob/0edbf2f31727bcc7a2131d2d68284ad68832502c/composeApp/src/androidMain/kotlin/shared/DatabaseDriverFactory.android.kt#L15-L14
// where we need to have application context in case of Android.
// refer to this https://proandroiddev.com/how-to-avoid-asking-for-android-context-in-kotlin-multiplatform-libraries-api-d280a4adebd2
public class KLocationInitializer: Initializer<KLocationContext> {
    override fun create(context: Context): KLocationContext {
        applicationContext = context.applicationContext
        return KLocationContext
    }

    override fun dependencies(): List<Class<out Initializer<*>>> {
        return emptyList()
    }
}