package org.chasonchoate.ktratpackposts.models

import org.jetbrains.exposed.dao.IntIdTable

object UsersTable : IntIdTable("users") {
    val created = date("created")
    val firstName = varchar("first_name", 255)
    val lastName = varchar("last_name", 255)
    val email = varchar("email", 255)

    val salt = varchar("salt", 255)
    val encryptedPassword = varchar("encrypted_password", 255)
}