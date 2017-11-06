package org.chasonchoate.ktratpackposts.models

import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.mindrot.jbcrypt.BCrypt

class User(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<User>(UsersTable)

    var created by UsersTable.created
    var firstName by UsersTable.firstName
    var lastName by UsersTable.lastName
    var email by UsersTable.email

    var salt by UsersTable.salt
        private set
    var encryptedPassword by UsersTable.encryptedPassword
        private set

    fun encryptPassword(plainTextPassword: String) {
        salt = BCrypt.gensalt()
        encryptedPassword = BCrypt.hashpw(plainTextPassword, salt)
    }

    fun checkPassword(plainTextPassword: String): Boolean {
        return BCrypt.checkpw(plainTextPassword, encryptedPassword)
    }

    override fun toString(): String {
        return "User{id=$id, created=$created, firstName=$firstName, lastName=$lastName, email=$email}"
    }
}