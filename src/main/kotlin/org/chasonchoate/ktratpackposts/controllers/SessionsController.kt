package org.chasonchoate.ktratpackposts.controllers

import freemarker.template.Configuration
import org.chasonchoate.ktratpackposts.commons.Controller
import org.chasonchoate.ktratpackposts.models.Session
import org.chasonchoate.ktratpackposts.models.User
import org.chasonchoate.ktratpackposts.models.UsersTable
import org.jetbrains.exposed.sql.transactions.transaction
import org.joda.time.DateTime
import ratpack.form.Form
import ratpack.handling.Chain
import ratpack.handling.Context

class SessionsController(chain: Chain, templateCfg: Configuration) : Controller(templateCfg) {
    init {
        chain.get("new") { this.new(it) }
        chain.path {
            it.byMethod { method ->
                method.post { ctx -> this.create(ctx) }
            }
        }
        chain.path(":id") {
            it.byMethod { method ->
                method.delete { ctx -> this.delete(ctx) }
            }
        }
    }

    fun new(ctx: Context) {
        respondWithTemplate(ctx, "sessions/new.ftl")
    }

    fun create(ctx: Context) {
        val form = ctx.parse(Form::class.java)

        form.then { f ->
            transaction {
                val email = f["email"] ?: "Unknown"
                val password = f["password"] ?: ""
                val requestedUser = User.find { UsersTable.email eq email }.singleOrNull()

                if (requestedUser != null && requestedUser.checkPassword(password)) {
                    Session.new {
                        created = DateTime.now()
                        user = requestedUser
                    }
                    ctx.redirect("/users")
                } else {
                    ctx.redirect("/sessions/new")
                }
            }
        }
    }

    fun delete(ctx: Context) {
        throw NotImplementedError()
    }
}