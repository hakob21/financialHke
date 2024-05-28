package com.hakob.financialhke.koin

import com.hakob.financialhke.BusinessLogic
import com.hakob.financialhke.db.repodomain.Budget
import com.hakob.financialhke.db.repodomain.Expense
import com.hakob.financialhke.db.repository.ExpenseRepositoryInterface
import com.hakob.financialhke.db.repositoryimpl.ExpenseRepository
import io.realm.kotlin.Configuration
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
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
//    single {
//        EntryQueries(
//            get(),
//        )
//    }
//    single {
//        SqlDelightEntryRepository(
//            get(),
//        )
//    }

    single {
        BusinessLogic(
            get()
        )
    }

//    single<EntryRepository> {
//        SqlDelightEntryRepository(
//            get()
//        )
//    }
//    single<DogApi> {
//        DogApiImpl(
//            getWith("DogApiImpl"),
//            get()
//        )
//    }


    single<ExpenseRepositoryInterface> { ExpenseRepository(get()) }

    single { Realm.open(get()) }

    single<Configuration> {
        RealmConfiguration.Builder(
            schema = setOf(
                // need add object class references here, which need to be added to the DB schema
                Expense::class,
                Budget::class
            )
        ).schemaVersion(1).build()
    }

//    single { UsersDb(get()) }
//    single { ReportsDb(get()) }

//    val configuration = RealmConfiguration.create(schema = setOf(Expense::class))
//    Realm.open(configuration)

}

