package com.github.simplyscala

import org.scalatest.FunSuite
import org.scalatest.matchers.ShouldMatchers
import com.novus.salat.dao.SalatDAO
import com.mongodb.casbah.MongoConnection
import com.mongodb.casbah.Imports._
import com.novus.salat.global._
import org.scalatest.exceptions.TestFailedException

class MongoEmbedDatabaseImmutableTest extends FunSuite with ShouldMatchers with MongoEmbedDatabase {
    test("fixture test with bad port") {
        evaluating {
            withEmbedMongoFixture(22222) { mongodProps =>
                Model2.save(Model2(name = "testFixture"))
                Model2.count() should be (1)
            }
        } should produce[com.mongodb.MongoException]
    }

    test("fixture test") {
        withEmbedMongoFixture(54321) { mongodProps =>
            Model2.save(Model2(name = "testFixture"))
            Model2.count() should be (1)
        }
    }

    test("launch fail() in fixture") {
        evaluating {
            withEmbedMongoFixture(54321) { mongodProps =>
                fail("fail")
            }
        } should produce[TestFailedException]
    }
}

case class Model2(id: ObjectId = new ObjectId, name: String)
object Model2 extends SalatDAO[Model2, ObjectId](collection = MongoConnection("localhost", 54321)("test")("model")) {}