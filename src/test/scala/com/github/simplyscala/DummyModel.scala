package com.github.simplyscala

import com.mongodb.casbah.MongoConnection
import com.mongodb.casbah.Imports._

case class DummyModel(id: ObjectId = new ObjectId, name: String)

trait DummyModelDAO {

  val collection: com.mongodb.casbah.Imports.MongoCollection

  private implicit val modelObjView = { dummy: DummyModel =>
    MongoDBObject(
      "_id" -> dummy.id,
      "name" -> dummy.name
    )
  }

  def save(dummy: DummyModel): WriteResult = {
    collection.save(dummy)
  }

  def count(): Int = {
    collection.count()
  }
}

object DummyModel extends DummyModelDAO {
  override val collection = MongoConnection("localhost", 12345)("test")("model")
}

object DummyModel2 extends DummyModelDAO {
  override val collection = MongoConnection("localhost", 33333)("test")("model")
}
