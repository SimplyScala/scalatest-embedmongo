package com.github.simplyscala

import org.scalatest.{BeforeAndAfter, FunSuite}
import org.scalatest.matchers.ShouldMatchers

class MongoEmbedDatabaseTest extends FunSuite with ShouldMatchers with BeforeAndAfter with MongoEmbedDatabase {

    var mongoProps: MongodProps = null

    before {
        mongoProps = mongoStart()
    }

    after {
        mongoStop(mongoProps)
    }

    test("test connection with embed mongodb") {
        DummyModel.save(DummyModel(name = "test"))
        DummyModel.count() should be (1)
    }
}


