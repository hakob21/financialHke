package com.hakob.financialhke.db.repodomain

import io.realm.kotlin.types.RealmObject

class Expense : RealmObject {
    var sum: Double = 0.0
}
