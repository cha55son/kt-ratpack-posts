package org.chasonchoate.ktratpackposts.controllers

import freemarker.template.Configuration
import org.chasonchoate.ktratpackposts.commons.Controller
import org.chasonchoate.ktratpackposts.models.User
import org.jetbrains.exposed.sql.transactions.transaction
import org.joda.time.DateTime
import ratpack.form.Form
import ratpack.handling.Chain
import ratpack.handling.Context

class UsersController(chain: Chain, templateCfg: Configuration) : Controller(templateCfg) {
    init {
        chain.path {
            it.byMethod { method ->
                method.get { ctx -> this.index(ctx) }
                method.post { ctx -> this.create(ctx) }
                // method.delete { ctx -> this.index(ctx) }
            }
        }
        chain.get("new") { this.new(it) }
    }

    fun index(ctx: Context) {
        val users = transaction { User.all().toList() }
        respondWithTemplate(ctx, "users/index.ftl", hashMapOf("users" to users))
    }

    fun new(ctx: Context) {
        respondWithTemplate(ctx, "users/new.ftl")
    }

    fun create(ctx: Context) {
        val form = ctx.parse(Form::class.java)

        form.then { f ->
            transaction {
                User.new {
                    created = DateTime.now()
                    firstName = f["firstName"] ?: "Unknown"
                    lastName = f["lastName"] ?: "Unknown"
                    email = f["email"] ?: "Unknown"
                    encryptPassword(f["password"] ?: "Unknown")
                }
            }
        }
        ctx.redirect("/users")
    }
}