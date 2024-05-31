package com.hakob.financialhke

import com.hakob.financialhke.db.repository.ExpenseRepositoryInterface
import com.hakob.financialhke.db.repositoryimpl.ExpenseRepository
import io.realm.kotlin.Configuration
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import org.koin.core.context.startKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.get
import org.koin.test.inject
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

// a test showing how you can use Koin dependency injection in tests
class SimpleKoinSampleTest : KoinTest {

    private val expenseRepository: ExpenseRepositoryInterface by inject()

    @Test
    fun `should inject my components`() {
        startKoin {
            modules(
                module {
                    single<ExpenseRepositoryInterface> { ExpenseRepository(get()) }

                    single { Realm.open(get()) }

                    single<Configuration> {
                        RealmConfiguration.Builder(
                            schema = setOf(
                                com.hakob.financialhke.db.repodomain.Expense::class
                            )
                        ).schemaVersion(1).build()
                    }
                })
        }

        // directly request an instance
        val componentA = get<Realm>()

        assertNotNull(componentA)
        assertEquals(componentA, (expenseRepository as ExpenseRepository).realm)
    }
}