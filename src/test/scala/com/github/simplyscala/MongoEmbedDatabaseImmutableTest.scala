package com.github.simplyscala

import org.scalatest.FunSuite
import org.scalatest.matchers.ShouldMatchers
import org.scalatest.exceptions.TestFailedException

class MongoEmbedDatabaseImmutableTest extends FunSuite with ShouldMatchers with MongoEmbedDatabase {
    test("fixture test with bad port") {
        evaluating {
            withEmbedMongoFixture(22222) { mongodProps =>
                DummyModel.save(DummyModel(name = "testFixture"))
                DummyModel.count() should be (1)
            }
        } should produce[com.mongodb.MongoException]
    }

    ignore("fixture test") {
        withEmbedMongoFixture(12345) { mongodProps =>
            DummyModel.save(DummyModel(name = "testFixture"))
            DummyModel.count() should be (1)
        }
    }

    test("launch fail() in fixture") {
        evaluating {
            withEmbedMongoFixture(12345) { mongodProps =>
                fail("fail")
            }
        } should produce[TestFailedException]
    }
}