package com.hakob.financialhke.db.repositoryimpl

import com.hakob.financialhke.domain.Expense
import com.hakob.financialhke.db.repodomain.Expense as RealmExpense
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import io.realm.kotlin.ext.query
import io.realm.kotlin.query.RealmQuery

class ExpenseRepository(private val realm: Realm) {
//    val realm: Realm by lazy {
//        val configuration = RealmConfiguration.create(schema = setOf(RealmExpense::class))
//        Realm.open(configuration)
//    }

    fun addExpense(expense: Expense): Expense {
        return realm.writeBlocking {
            copyToRealm(RealmExpense().apply { sum = expense.sum })
        }.toDomainExpense()
    }

    fun expenses(): List<Expense> {
        return realm.query<RealmExpense>().find()
            .map { realmExpense -> realmExpense.toDomainExpense() }
    }

    fun deleteAll() {
        realm.writeBlocking {
            val query: RealmQuery<RealmExpense> = this.query<RealmExpense>()
            delete(query)
        }
    }

    fun RealmExpense.toDomainExpense() =
        Expense(
            sum = this.sum
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
