package com.github.simplyscala

import org.scalatest.{BeforeAndAfter, FunSuite}
import org.scalatest.matchers.ShouldMatchers
import com.mongodb.casbah.MongoConnection
import com.mongodb.casbah.Imports._
import com.novus.salat.global._
import com.novus.salat.dao._

class MongoEmbedDatabaseTest extends FunSuite with ShouldMatchers with BeforeAndAfter with MongoEmbedDatabase {

    var mongoProps: MongodProps = null

    before {
        mongoProps = mongoStart()
    }

    after {
        mongoStop(mongoProps)
    }

    test("test connection with embed mongodb") {
        Model.save(Model(name = "test"))
        Model.count() should be (1)
    }

    test("test with fixture") {
        withEmbedMongoFixture(54321) { mongodProps =>
            Model.save(Model(name = "testFixture"))
            Model.count() should be (1)
        }
    }
}

case class Model(id: ObjectId = new ObjectId, name: String)
object Model extends SalatDAO[Model, ObjectId](collection = MongoConnection("localhost", 12345)("test")("model")) {}