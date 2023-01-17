package org.example

import com.exactpro.th2.cache.common.ArangoCredentials
import com.exactpro.th2.common.schema.factory.CommonFactory
import com.exactpro.th2.dataprovider.grpc.DataProviderService
import mu.KotlinLogging
import kotlin.system.measureTimeMillis

private val logger = KotlinLogging.logger {}

class Main(args: Array<String>) {
    private val commonFactory: CommonFactory
    private val context: Context
    private val configuration: Settings

    init {
        commonFactory = CommonFactory.createFromArguments(*args)
        configuration = commonFactory.getCustomConfiguration(Settings::class.java)
        context = Context( configuration)
    }

    fun run() {
        val arango = ArangoDBService(context)
        val a = measureTimeMillis { arango.transaction() }
        val b = measureTimeMillis { arango.f() }
        logger.info { "Transaction took $a ms\ninsertDocuments() took $b ms " }
    }
}

fun main(args: Array<String>) {
    Main(args).run()
}