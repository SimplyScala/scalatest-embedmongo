package com.github.simplyscala

import org.scalatest.{BeforeAndAfter, FunSuite}
import org.scalatest.matchers.ShouldMatchers

class MongoEmbedDatabaseTest extends FunSuite with ShouldMatchers with BeforeAndAfter with MongoEmbedDatabase {

    test("test connection with embed mongodb") {
        val mongoProcess = mongoStart()

        println( mongoProcess.getConfig )

        mongoStop(mongoProcess)
    }
}