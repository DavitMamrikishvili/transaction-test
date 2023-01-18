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

class ArangoDBService(appCtx: Context) {
    private val config = appCtx.configuration
    private val arango: Arango = Arango(config.arangoCredentials)
    private val db = arango.getDatabase()
    private val events1 = (1..20_000).map {
        Event("862a3cb7-79f7-4f33-93ee-8797bee7e439:3fad95f4-3881-11ec-8760-$it",  "book", "scope","862a3cb7-79f7-4f33-93ee", "862a3cb7-79f7-4f33-93ee-8797bee7e439", false, "name", "type", 1751358236167872, 1751358236167879, "862a3cb7-79f7-4f33-93ee-8797bee7e439:3fad47d2-3881-11ec-8760-e55c32c0bc13", true, setOf(),
        "xwewsecchmtibgnkiwdfutitniyjltabnpawbkxpuebicuprioefuglxxkrtwjhxgvtadqmpfnxtvegevcyerixjqntqntqqahlytxuaavtinoajlqrejzcvqoswytprimcepebqrbjpabrxqzbwwzlojvlmztexeijyujwkmixqeleyaqniortbwdgfxecpvaqbndajcrjyhrazyojjployqybhrtghimiimpqpplrsmufxicnvfnpanlpntxilchfrezgeqtwqgfisrnxpatjsmvizwrayeapifnabvvejeuqspoifnvmlscapuxzdpccwueptarinrkwnpfgcpxsjhbxkmrvlarepjentaozfdzhjadojdukyphraugginbpqnnwlxooozyxokvnowaoicangcuegvgngiwvzaudlhayxkhzcwcdqdonfmcgsnzmwfcexjdxgzeaxhzeyxlzmykxrqoanwakgpikgazbpcwmjpojgkghmyhawcdlnmxpesokjztkmlmwtqjnmtwtvftelkjyibanawzkyqdevfaayloingxkamjrptnkqdrfepxqcxmudqfdxekjmpcxidnlvkfpxgxolwrhetilmrcpxmngbnhewarsijkseyfzbryfpbnzpyctobzczefxibezcemglpzqitcfgrwawjovikpdlkqgzzaheapurhxygujfxhylomqeheircdqgzyugurmnsdhqrrgaxghtecweklnapjkyqrhtrplepqgjvxzogapcbsmsloqcgsvdjwngdlvnmbpmnqaxzxvyfluyjqorrkfsxmoqstkxloofrwwkexovaktsfrhbhidhuqzmqikbatbunrzlmwmvjtiyyjotacaspmbnnzkdzipuhrptftzcusrekocpjzsabsefdeuimjnaegbtdrecpsktbaylfcogukhwinwrrooijctgucozbzdqtwnjuokdbpxndnunqbbcwfgaj")
    }.toList()
    private val events2 = (20_001..40_000).map {
        Event("862a3cb7-79f7-4f33-93ee-8797bee7e439:3fad95f4-3881-11ec-8760-$it",  "book", "scope","862a3cb7-79f7-4f33-93ee", "862a3cb7-79f7-4f33-93ee-8797bee7e439", false, "name", "type", 1751358236167872, 1751358236167879, "862a3cb7-79f7-4f33-93ee-8797bee7e439:3fad47d2-3881-11ec-8760-e55c32c0bc13", true, setOf(),
            "xwewsecchmtibgnkiwdfutitniyjltabnpawbkxpuebicuprioefuglxxkrtwjhxgvtadqmpfnxtvegevcyerixjqntqntqqahlytxuaavtinoajlqrejzcvqoswytprimcepebqrbjpabrxqzbwwzlojvlmztexeijyujwkmixqeleyaqniortbwdgfxecpvaqbndajcrjyhrazyojjployqybhrtghimiimpqpplrsmufxicnvfnpanlpntxilchfrezgeqtwqgfisrnxpatjsmvizwrayeapifnabvvejeuqspoifnvmlscapuxzdpccwueptarinrkwnpfgcpxsjhbxkmrvlarepjentaozfdzhjadojdukyphraugginbpqnnwlxooozyxokvnowaoicangcuegvgngiwvzaudlhayxkhzcwcdqdonfmcgsnzmwfcexjdxgzeaxhzeyxlzmykxrqoanwakgpikgazbpcwmjpojgkghmyhawcdlnmxpesokjztkmlmwtqjnmtwtvftelkjyibanawzkyqdevfaayloingxkamjrptnkqdrfepxqcxmudqfdxekjmpcxidnlvkfpxgxolwrhetilmrcpxmngbnhewarsijkseyfzbryfpbnzpyctobzczefxibezcemglpzqitcfgrwawjovikpdlkqgzzaheapurhxygujfxhylomqeheircdqgzyugurmnsdhqrrgaxghtecweklnapjkyqrhtrplepqgjvxzogapcbsmsloqcgsvdjwngdlvnmbpmnqaxzxvyfluyjqorrkfsxmoqstkxloofrwwkexovaktsfrhbhidhuqzmqikbatbunrzlmwmvjtiyyjotacaspmbnnzkdzipuhrptftzcusrekocpjzsabsefdeuimjnaegbtdrecpsktbaylfcogukhwinwrrooijctgucozbzdqtwnjuokdbpxndnunqbbcwfgaj")
    }.toList()

    fun transaction() {
        val tx = db.beginStreamTransaction(StreamTransactionOptions().writeCollections(EVENT_COLLECTION, EVENT_EDGES))
        val options = DocumentCreateOptions().streamTransactionId(tx.id)
        db.collection(EVENT_COLLECTION).insertDocuments(events1, options)
        db.commitStreamTransaction(tx.id)
    }

    fun f() {
        db.collection(EVENT_COLLECTION).insertDocuments(events2)
    }
}
