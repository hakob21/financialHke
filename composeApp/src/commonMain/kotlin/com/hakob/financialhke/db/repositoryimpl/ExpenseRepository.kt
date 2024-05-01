package com.hakob.financialhke.db.repositoryimpl

import com.hakob.financialhke.db.repository.ExpenseRepositoryInterface
import com.hakob.financialhke.domain.Budget
import com.hakob.financialhke.db.repodomain.Budget as RealmBudget
import com.hakob.financialhke.domain.Expense
import com.hakob.financialhke.realmUtils.toInstant
import com.hakob.financialhke.realmUtils.toRealmInstant
import com.hakob.financialhke.db.repodomain.Expense as RealmExpense
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import io.realm.kotlin.query.RealmQuery
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

class ExpenseRepository(private val realm: Realm) : ExpenseRepositoryInterface {
//    val realm: Realm by lazy {
//        val configuration = RealmConfiguration.create(schema = setOf(RealmExpense::class))
//        Realm.open(configuration)
//    }

    override fun addExpense(expense: Expense): Expense {
        return realm.writeBlocking {
            copyToRealm(RealmExpense().apply { sum = expense.sum })
        }.toDomainExpense()
    }

    override fun setBudget(budget: Budget): Budget {
        return realm.writeBlocking {
            copyToRealm(RealmBudget().apply {
                sum = budget.sum
                realmInstant = Instant.parse(budget.localDate.toString()).toRealmInstant()
            })
        }.toDomainBudget()

    }

    override fun expenses(): List<Expense> {
        return realm.query<RealmExpense>().find()
            .map { realmExpense -> realmExpense.toDomainExpense() }
    }

    override fun deleteAll() {
        realm.writeBlocking {
            val query: RealmQuery<RealmExpense> = this.query<RealmExpense>()
            delete(query)
        }
    }

    fun RealmExpense.toDomainExpense() =
        Expense(
            sum = this.sum
        )

    fun RealmBudget.toDomainBudget() =
        Budget(
            sum = this.sum,
            localDate = this.realmInstant.toInstant().toLocalDateTime(TimeZone.currentSystemDefault()).date,
        )
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
