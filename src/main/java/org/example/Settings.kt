package org.example

import com.exactpro.th2.cache.common.ArangoCredentials

data class Settings(
    val hostname: String = "localhost",
    val port: Int = 8080,
    val responseTimeout: Int = 60000,
    val arangoCredentials: ArangoCredentials
)