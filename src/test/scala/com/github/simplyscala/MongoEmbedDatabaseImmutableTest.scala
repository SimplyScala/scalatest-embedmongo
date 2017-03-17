package com.github.simplyscala

import org.scalatest.{FunSuite, Matchers}
import org.scalatest.exceptions.TestFailedException

class MongoEmbedDatabaseImmutableTest extends FunSuite with Matchers with MongoEmbedDatabase {
    test("fixture test with bad port") {
        an[com.mongodb.MongoException] should be thrownBy {
            withEmbedMongoFixture(22222) { mongodProps =>
                DummyModel.save(DummyModel(name = "testFixture"))
                DummyModel.count() should be (1)
            }
        }
    }

    test("fixture test") {
        withEmbedMongoFixture(12345) { mongodProps =>
            DummyModel.save(DummyModel(name = "testFixture"))
            DummyModel.count() should be (1)
        }
    }

    test("launch fail() in fixture") {
        an[TestFailedException] should be thrownBy {
            withEmbedMongoFixture(12345) { mongodProps =>
                fail("fail")
            }
        }
    }
}