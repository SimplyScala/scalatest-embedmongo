package com.github.simplyscala

import org.scalatest.{BeforeAndAfter, FunSuite, Matchers}

class MongoEmbedDatabaseTest extends FunSuite with Matchers with BeforeAndAfter with MongoEmbedDatabase {

    var mongoProps: MongodProps = null

    before { mongoProps = mongoStart(33333) }

    after { mongoStop(mongoProps) }

    test("test connection with embed mongodb") {
        DummyModel2.save(DummyModel(name = "test"))
        DummyModel2.count() should be(1)
    }
}