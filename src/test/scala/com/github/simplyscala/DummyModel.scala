package com.github.simplyscala

import com.novus.salat.dao.SalatDAO
import com.mongodb.casbah.MongoConnection
import com.mongodb.casbah.Imports._
import com.novus.salat.global._

case class DummyModel(id: ObjectId = new ObjectId, name: String)
object DummyModel extends SalatDAO[DummyModel, ObjectId](collection = MongoConnection("localhost", 12345)("test")("model")) {}
object DummyModel2 extends SalatDAO[DummyModel, ObjectId](collection = MongoConnection("localhost", 33333)("test")("model")) {}
