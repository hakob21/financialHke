package com.hakob.financialhke.db.repositoryimpl

import com.hakob.financialhke.db.repository.ExpenseRepositoryInterface
import com.hakob.financialhke.domain.Budget
import com.hakob.financialhke.db.repodomain.Budget as RealmBudget
import com.hakob.financialhke.domain.Expense
import com.hakob.financialhke.realmUtils.toInstant
import com.hakob.financialhke.realmUtils.toRealmInstant
import com.hakob.financialhke.db.repodomain.Expense as RealmExpense
import io.realm.kotlin.Realm
import io.realm.kotlin.UpdatePolicy
import io.realm.kotlin.ext.query
import io.realm.kotlin.internal.getIdentifierOrNull
import io.realm.kotlin.query.RealmQuery
import io.realm.kotlin.query.find
import io.wojciechosak.calendar.utils.toMonthYear
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime

class ExpenseRepository(val realm: Realm) : ExpenseRepositoryInterface {
//    val realm: Realm by lazy {
//        val configuration = RealmConfiguration.create(schema = setOf(RealmExpense::class))
//        Realm.open(configuration)
//    }

    override fun addExpense(expense: Expense): Expense {
        val currentLocalDateTime: LocalDateTime =
            Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
        val currentMonthBudget = getCurrentBudget(currentLocalDateTime)
//        val updatedBudget = currentMonthBudget.copy(sum = currentMonthBudget.sum - expense.sum)
        // updateBudget
//        setBudget(updatedBudget)

        realm.writeBlocking {
            val frozenBudget = realm.query<RealmBudget>().find().first()
            println("Hke frozenBudget ${frozenBudget}")
            println("Hke frozenBudget id ${frozenBudget.getIdentifierOrNull()}")
            val latest = findLatest(frozenBudget)
            println("hke latest $latest")
            findLatest(frozenBudget)?.let { liveBudget ->
                // for some reason a block { } doesn't run. todo: figure out why
//                {
//                    println("hke liveBudget ${liveBudget.sum}")
//                    println("hke expense ${expense.sum}")
                liveBudget.sum -= expense.sum
//                }
            }
        }

        println("hke getCurrentBudget ${getCurrentBudget(currentLocalDateTime)}")
        return realm.writeBlocking {
            copyToRealm(RealmExpense().apply { sum = expense.sum })
        }.toDomainExpense()
    }

    override fun setBudget(budget: Budget): Budget {
        return realm.writeBlocking {
            copyToRealm(
                RealmBudget().apply {
                    _id = budget.endLocalDateTime.date.toMonthYear().toString()
                    sum = budget.sum
                    // temporarily appending "Z" to the localDateTime property
                    realmInstant = Instant.parse(budget.endLocalDateTime.toString() + "Z")
                        .toRealmInstant() //toRealmInstant() will convert localTime to UTC
                },
                UpdatePolicy.ALL
            )
        }.toDomainBudget()

    }

    override fun expenses(): List<Expense> {
        return realm.query<RealmExpense>().find()
            .map { realmExpense -> realmExpense.toDomainExpense() }
    }

    override fun getCurrentBudget(localDateTime: LocalDateTime): Budget {
        println("hke all budgets")
        println(realm.query<RealmBudget>().find { it.forEach { println(it.sum) } })
        println(realm.query<RealmBudget>().find { it.count() })
        val localDateTimeOfFirstMinuteOfTheDay =
            LocalDateTime(localDateTime.date, LocalTime(0, 0, 0))
        val localDateTimeOfLastMinuteOfTheDay =
            LocalDateTime(localDateTime.date, LocalTime(23, 59, 59, 59))
        val instantOfFirstMinuteOfTheDay =
            localDateTimeOfFirstMinuteOfTheDay.toInstant(TimeZone.UTC)
        val realmInstant = instantOfFirstMinuteOfTheDay.toRealmInstant()
        return realm
            .query<RealmBudget>("realmInstant >= $0", realmInstant)
            .find()
            .first()
            .toDomainBudget()
    }

    override fun deleteAllExpenses() {
        realm.writeBlocking {
            val query: RealmQuery<RealmExpense> = this.query<RealmExpense>()
            delete(query)
        }
    }

    override fun deleteAllBudgets() {
        realm.writeBlocking {
            val query: RealmQuery<RealmBudget> = this.query<RealmBudget>()
            delete(query)
        }
    }

    fun RealmExpense.toDomainExpense() =
        Expense(
            sum = this.sum
        )

    fun RealmBudget.toDomainBudget(): Budget {
        println("hke realmInstantToInstant ${this.realmInstant.toInstant()}")
        println(
            "hke realmInstantToInstant.toLocalDateTime ${
                this.realmInstant.toInstant().toLocalDateTime(TimeZone.currentSystemDefault())
            }"
        )
        println(
            "hke realmInstantToInstant.UTC ${
                this.realmInstant.toInstant().toLocalDateTime(TimeZone.UTC)
            }"
        )
        return Budget(
            sum = this.sum,
            endLocalDateTime = this.realmInstant.toInstant().toLocalDateTime(TimeZone.UTC),
        )
    }
}

//class ExpressionRepository {
//
//    val realm: Realm by lazy {
//        val configuration = RealmConfiguration.create(schema = setOf(Expression::class))
//        Realm.open(configuration)
//    }
//
//    fun addExpression(expression: String): Expression {
//        return realm.writeBlocking {
//            copyToRealm(Expression().apply { expressionString = expression })
//        }
//    }
//
//    fun expressions(): List<Expression> {
//        return realm.query<Expression>().find()
//    }
//
//    fun observeChanges(): Flow<List<Expression>> {
//        return realm.query<Expression>().asFlow().map { it.list }
//    }
//}
