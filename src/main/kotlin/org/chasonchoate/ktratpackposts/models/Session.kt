package org.chasonchoate.ktratpackposts.models

import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass

class Session(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<Session>(SessionsTable)

    var created by SessionsTable.created
    var user by User referencedOn SessionsTable.user
}
