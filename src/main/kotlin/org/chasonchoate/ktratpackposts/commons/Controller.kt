package org.chasonchoate.ktratpackposts.commons

import freemarker.template.Configuration
import ratpack.handling.Context
import ratpack.http.MediaType
import java.io.StringWriter

open class Controller(private val templateCfg: Configuration) {

    fun renderTemplate(templatePath: String, data: Map<String, Any?>): String {
        val template = templateCfg.getTemplate(templatePath)
        val writer = StringWriter()
        template.process(data, writer)
        return writer.toString()
    }

    fun respondWithTemplate(ctx: Context, templatePath: String, data: Map<String, Any?>) {
        val html = renderTemplate(templatePath, data)
        ctx.response.contentType(MediaType.TEXT_HTML).send(html)
    }

    fun respondWithTemplate(ctx: Context, templatePath: String) {
        respondWithTemplate(ctx, templatePath, emptyMap<String, Any>())
    }
}