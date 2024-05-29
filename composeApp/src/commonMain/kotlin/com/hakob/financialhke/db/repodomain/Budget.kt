package com.hakob.financialhke.db.repodomain

import com.hakob.financialhke.realmUtils.toRealmInstant
import io.realm.kotlin.types.RealmInstant
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import org.mongodb.kbson.ObjectId

class Budget: RealmObject {
    @PrimaryKey
    var _id: String = ObjectId().toString()

    var sum: Double = 0.0
    var realmInstant: RealmInstant = Instant.parse(Clock.System.now().toString()).toRealmInstant()
}
