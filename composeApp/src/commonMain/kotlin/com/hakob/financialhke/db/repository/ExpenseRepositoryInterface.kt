package com.hakob.financialhke.db.repository

import com.hakob.financialhke.domain.Budget
import com.hakob.financialhke.domain.Expense
import com.hakob.financialhke.db.repodomain.Expense as RealmExpense
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import io.realm.kotlin.ext.query
import io.realm.kotlin.query.RealmQuery

interface ExpenseRepositoryInterface {
//    val realm: Realm by lazy {
//        val configuration = RealmConfiguration.create(schema = setOf(RealmExpense::class))
//        Realm.open(configuration)
//    }

    fun addExpense(expense: Expense): Expense

    fun setBudget(budget: Budget): Budget

    fun expenses(): List<Expense>

    fun deleteAll()
}
