package org.chasonchoate.ktratpackposts

import freemarker.template.Configuration
import freemarker.template.TemplateExceptionHandler
import org.chasonchoate.ktratpackposts.commons.Controller
import org.chasonchoate.ktratpackposts.controllers.HelloController
import org.chasonchoate.ktratpackposts.controllers.SessionsController
import org.chasonchoate.ktratpackposts.controllers.UsersController
import org.chasonchoate.ktratpackposts.models.SessionsTable
import org.chasonchoate.ktratpackposts.models.UsersTable
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.Transaction
import org.jetbrains.exposed.sql.transactions.TransactionManager
import org.jetbrains.exposed.sql.transactions.transaction
import ratpack.server.RatpackServer
import java.sql.Connection

fun main(args: Array<String>) {
    val templateCfg = Configuration(Configuration.VERSION_2_3_27)
    templateCfg.defaultEncoding = "UTF-8"
    templateCfg.templateExceptionHandler = TemplateExceptionHandler.HTML_DEBUG_HANDLER
    templateCfg.setClassForTemplateLoading(Controller::class.java, "/")
    templateCfg.logTemplateExceptions = false
    templateCfg.wrapUncheckedExceptions = true

    Database.connect("jdbc:sqlite:file:ktratpackposts.sqlite", driver = "org.sqlite.JDBC")
    TransactionManager.manager.defaultIsolationLevel = Connection.TRANSACTION_SERIALIZABLE
    transaction {
        SchemaUtils.create(UsersTable, SessionsTable)
    }

    RatpackServer.start { server ->
        server.serverConfig {
            it.port(3000)
        }
        server.handlers { chain ->
            chain.prefix("users", { UsersController(it, templateCfg) })
            chain.prefix("sessions", { SessionsController(it, templateCfg) })
            chain.prefix("hello", { HelloController(it, templateCfg) })
        }
    }
}