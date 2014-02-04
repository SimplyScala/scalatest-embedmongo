package com.github.simplyscala

import org.scalatest.{BeforeAndAfter, FunSuite}
import org.scalatest.matchers.ShouldMatchers

class MongoEmbedDatabaseTest extends FunSuite with ShouldMatchers with BeforeAndAfter with MongoEmbedDatabase {

    var mongoProps: MongodProps = null

    before { mongoProps = mongoStart(33333) }

    after { mongoStop(mongoProps) }

    test("test connection with embed mongodb") {
        DummyModel2.save(DummyModel(name = "test"))
        DummyModel2.count() should be(1)
    }
}