package org.chasonchoate.ktratpackposts.models

import org.jetbrains.exposed.dao.IntIdTable

object SessionsTable : IntIdTable("sessions") {
    val created = date("created")
    val user = reference("user", UsersTable)

    override fun toString(): String {
        return "Session{id=$id, created=$created, user=$user}"
    }
}