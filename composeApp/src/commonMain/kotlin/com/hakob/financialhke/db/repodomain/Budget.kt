package com.hakob.financialhke.db.repodomain

import com.hakob.financialhke.realmUtils.toRealmInstant
import io.realm.kotlin.types.RealmInstant
import io.realm.kotlin.types.RealmObject
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant

class Budget: RealmObject {
    var sum: Double = 0.0
    var realmInstant: RealmInstant = Instant.parse(Clock.System.now().toString()).toRealmInstant()
}
