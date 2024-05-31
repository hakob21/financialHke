//package com.hakob.financialhke
//
//import com.hakob.financialhke.codeUtils.ActualClockProvider
//import com.hakob.financialhke.codeUtils.ClockProvider
//import com.hakob.financialhke.db.repodomain.Budget
//import com.hakob.financialhke.db.repodomain.Expense
//import com.hakob.financialhke.db.repository.ExpenseRepositoryInterface
//import com.hakob.financialhke.db.repositoryimpl.ExpenseRepository
//import io.realm.kotlin.Configuration
//import io.realm.kotlin.Realm
//import io.realm.kotlin.RealmConfiguration
//import org.koin.core.context.startKoin
//import org.koin.dsl.module
//import org.koin.test.KoinTest
//import org.koin.test.get
//import org.koin.test.inject
//import kotlin.test.Test
//import kotlin.test.assertEquals
//import kotlin.test.assertNotNull
//
//// a test showing how you can use Koin dependency injection in tests
//class SimpleKoinSampleTest : KoinTest {
//
//    private val expenseRepository: ExpenseRepositoryInterface by inject()
//
//    @Test
//    fun `should inject my components`() {
//        startKoin {
//            modules(
//                module {
////    single {
////        EntryQueries(
////            get(),
////        )
////    }
////    single {
////        SqlDelightEntryRepository(
////            get(),
////        )
////    }
//
//                    single {
//                        BusinessLogic(
//                            get(),
//                            get()
//                        )
//                    }
//
//                    single<ClockProvider> {
//                        ActualClockProvider()
//                    }
//
////    single<EntryRepository> {
////        SqlDelightEntryRepository(
////            get()
////        )
////    }
////    single<DogApi> {
////        DogApiImpl(
////            getWith("DogApiImpl"),
////            get()
////        )
////    }
//
//
//                    single<ExpenseRepositoryInterface> { ExpenseRepository(get()) }
//
//                    single { Realm.open(get()) }
//
//                    single<Configuration> {
//                        RealmConfiguration.Builder(
//                            schema = setOf(
//                                // need add object class references here, which need to be added to the DB schema
//                                Expense::class,
//                                Budget::class
//                            )
//                        ).schemaVersion(1).deleteRealmIfMigrationNeeded().build()
//                    }
//
////    single { UsersDb(get()) }
////    single { ReportsDb(get()) }
//
////    val configuration = RealmConfiguration.create(schema = setOf(Expense::class))
////    Realm.open(configuration)
//
//                })
//        }
//
//        // directly request an instance
//        val componentA = get<Realm>()
//
//        assertNotNull(componentA)
//        assertEquals(componentA, (expenseRepository as ExpenseRepository).realm)
//    }
//}