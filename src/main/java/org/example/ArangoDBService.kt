package org.example

import com.arangodb.entity.BaseEdgeDocument
import com.arangodb.model.DocumentCreateOptions
import com.arangodb.model.StreamTransactionOptions
import com.exactpro.th2.cache.common.Arango
import com.exactpro.th2.cache.common.Arango.Companion.EVENT_COLLECTION
import com.exactpro.th2.cache.common.Arango.Companion.EVENT_EDGES
import com.exactpro.th2.cache.common.event.Event
import mu.KotlinLogging
import kotlin.system.measureTimeMillis

private val logger = KotlinLogging.logger {}

class ArangoDBService(appCtx: Context) {
    private val config = appCtx.configuration
    private val arango: Arango = Arango(config.arangoCredentials)
    private val db = arango.getDatabase()
    private val events = (1..200_000).map {
        Event("$it",  "", "","", "", false, "name", "type", 123120, 12312, "", true, setOf(),
        "xwewsecchmtibgnkiwdfutitniyjltabnpawbkxpuebicuprioefuglxxkrtwjhxgvtadqmpfnxtvegevcyerixjqntqntqqahlytxuaavtinoajlqrejzcvqoswytprimcepebqrbjpabrxqzbwwzlojvlmztexeijyujwkmixqeleyaqniortbwdgfxecpvaqbndajcrjyhrazyojjployqybhrtghimiimpqpplrsmufxicnvfnpanlpntxilchfrezgeqtwqgfisrnxpatjsmvizwrayeapifnabvvejeuqspoifnvmlscapuxzdpccwueptarinrkwnpfgcpxsjhbxkmrvlarepjentaozfdzhjadojdukyphraugginbpqnnwlxooozyxokvnowaoicangcuegvgngiwvzaudlhayxkhzcwcdqdonfmcgsnzmwfcexjdxgzeaxhzeyxlzmykxrqoanwakgpikgazbpcwmjpojgkghmyhawcdlnmxpesokjztkmlmwtqjnmtwtvftelkjyibanawzkyqdevfaayloingxkamjrptnkqdrfepxqcxmudqfdxekjmpcxidnlvkfpxgxolwrhetilmrcpxmngbnhewarsijkseyfzbryfpbnzpyctobzczefxibezcemglpzqitcfgrwawjovikpdlkqgzzaheapurhxygujfxhylomqeheircdqgzyugurmnsdhqrrgaxghtecweklnapjkyqrhtrplepqgjvxzogapcbsmsloqcgsvdjwngdlvnmbpmnqaxzxvyfluyjqorrkfsxmoqstkxloofrwwkexovaktsfrhbhidhuqzmqikbatbunrzlmwmvjtiyyjotacaspmbnnzkdzipuhrptftzcusrekocpjzsabsefdeuimjnaegbtdrecpsktbaylfcogukhwinwrrooijctgucozbzdqtwnjuokdbpxndnunqbbcwfgaj")
    }.toList()

    fun transaction() {
        val tx = db.beginStreamTransaction(StreamTransactionOptions().writeCollections(EVENT_COLLECTION, EVENT_EDGES))
        val options = DocumentCreateOptions().streamTransactionId(tx.id)
        val a = measureTimeMillis {
            db.collection(EVENT_COLLECTION).insertDocuments(events.subList(1, 100_001), options)
            db.commitStreamTransaction(tx.id)
        }
        logger.info { "Transaction took $a ms" }
    }

    fun f() {
        val a = measureTimeMillis { db.collection(EVENT_COLLECTION).insertDocuments(events.subList(100_001, 200_001)) }
        logger.info { "insertDocuments() took $a ms" }
    }
}
