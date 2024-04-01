package koin

import EntryDataSource
import com.hakob.financialhke.Greeting
import database.EntryQueries
import db.SqlDelightEntryDataSource
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.module

fun initKoin(appModule: Module): KoinApplication {
    val koinApplication = startKoin {
        modules(
            appModule,
            platformModule,
            coreModule()
        )
    }

    // Dummy initialization logic, making use of appModule declarations for demonstration purposes.
    val koin = koinApplication.koin
    // doOnStartup is a lambda which is implemented in Swift on iOS side
    val doOnStartup = koin.get<() -> Unit>()
    doOnStartup.invoke()

//    val kermit = koin.get<Logger> { parametersOf(null) }
    // AppInfo is a Kotlin interface with separate Android and iOS implementations
//    val appInfo = koin.get<AppInfo>()
//    kermit.v { "App Id ${appInfo.appId}" }

    return koinApplication
}

expect val platformModule: Module

fun coreModule() = module {
    single {
        EntryQueries(
            get(),
        )
    }
    single {
        SqlDelightEntryDataSource(
            get(),
        )
    }

    single {
        Greeting(
            get()
        )
    }

    single<EntryDataSource> {
        SqlDelightEntryDataSource(
            get()
        )
    }
//    single<DogApi> {
//        DogApiImpl(
//            getWith("DogApiImpl"),
//            get()
//        )
//    }
}

