package org.example

import com.exactpro.th2.cache.common.ArangoCredentials
import com.exactpro.th2.common.schema.factory.CommonFactory
import com.exactpro.th2.dataprovider.grpc.DataProviderService

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
        arango.transaction()
        arango.f()
    }
}

fun main(args: Array<String>) {
    Main(args).run()
}