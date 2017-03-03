package com.github.simplyscala

import com.github.simplyscala.Helpers._
import org.mongodb.scala.bson.collection.immutable.Document
import org.scalatest.exceptions.TestFailedException
import org.scalatest.{FunSuite, Matchers}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success}

class MongoEmbedDatabaseImmutableTest extends FunSuite with Matchers with MongoEmbedDatabase {
  test("fixture test with bad port") {
    withEmbedMongoFixture(22222) { mongodProps =>
      val conn = Connector.getCollection(Connector.conf1)
      val future = conn.insertOne(document = Document("name" -> "testFixture")).toFuture
      future onComplete {
        case Success(succ) => fail("must not connect to this port")
        case Failure(ex) => ex shouldBe an[com.mongodb.MongoException
          ]
      }
    }

  }

  ignore("fixture test") {
    withEmbedMongoFixture(12345) { mongodProps =>
      val conn = Connector.getCollection(Connector.conf1)
      conn.insertOne(document = Document("name" -> "testFixture")).results()
      conn.count().results() should be(1)
    }
  }

  test("launch fail() in fixture") {
    the[TestFailedException] thrownBy {
      withEmbedMongoFixture(12345) { mongodProps =>
        fail("fail")
      }
    }
  }
}
