package org.chasonchoate.ktratpackposts.controllers

import freemarker.template.Configuration
import org.chasonchoate.ktratpackposts.commons.Controller
import ratpack.handling.Chain
import ratpack.handling.Context

class HelloController(chain: Chain, templateCfg: Configuration) : Controller(templateCfg) {
    init {
        chain.get { this.index(it) }
        chain.get(":name") { this.name(it) }
    }

    fun index(ctx: Context) {
        ctx.render("Hello, world!")
    }

    fun name(ctx: Context) {
        val name = ctx.pathTokens["name"]
        respondWithTemplate(ctx, "hello.ftl", hashMapOf("name" to name))
    }
}