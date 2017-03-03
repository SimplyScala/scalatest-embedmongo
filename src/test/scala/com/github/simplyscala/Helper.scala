package com.github.simplyscala

import java.util.concurrent.TimeUnit

import org.mongodb.scala._

import scala.concurrent.Await
import scala.concurrent.duration.Duration

object Connector {

  case class MongoConfig(connectionUrl: String, database: String, collectionName: String)

  def getCollection(mongoConfig: MongoConfig): MongoCollection[Document] = {
    val mongoClient: MongoClient = MongoClient(mongoConfig.connectionUrl)
    val database = mongoClient.getDatabase(mongoConfig.database)
    database.getCollection(mongoConfig.collectionName)
  }

  val conf1 = MongoConfig("mongodb://localhost:12345", "test", "model")
  val conf2 = MongoConfig("mongodb://localhost:33333", "test", "model")
}

// this helper copied from:
// https://github.com/mongodb/mongo-scala-driver/blob/master/examples/src/test/scala/tour/Helpers.scala
object Helpers {

  implicit class DocumentObservable[C](val observable: Observable[Document]) extends ImplicitObservable[Document] {
    override val converter: (Document) => String = (doc) => doc.toJson
  }

  implicit class GenericObservable[C](val observable: Observable[C]) extends ImplicitObservable[C] {
    override val converter: (C) => String = (doc) => doc.toString
  }

  trait ImplicitObservable[C] {
    val observable: Observable[C]
    val converter: (C) => String

    def results(): Seq[C] = Await.result(observable.toFuture(), Duration(10, TimeUnit.SECONDS))

    def headResult() = Await.result(observable.head(), Duration(10, TimeUnit.SECONDS))

    def printResults(initial: String = ""): Unit = {
      if(initial.length > 0) print(initial)
      results().foreach(res => println(converter(res)))
    }

    def printHeadResult(initial: String = ""): Unit = println(s"${initial}${converter(headResult())}")
  }

}
