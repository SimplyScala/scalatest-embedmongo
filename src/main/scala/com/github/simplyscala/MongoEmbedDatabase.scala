package com.github.simplyscala

import de.flapdoodle.embed.mongo.distribution.Version
import de.flapdoodle.embed.mongo.{MongodProcess, MongodExecutable, MongodStarter}
import de.flapdoodle.embed.mongo.config.MongodConfig

/**
 * Extends this trait provide to your test class a connection to embededMongoDb database
 */
trait MongoEmbedDatabase {

    private lazy val runtime: MongodStarter = MongodStarter.getDefaultInstance
    private lazy val mongoExe: MongodExecutable = runtime.prepare(new MongodConfig(embedMongoDBVersion, embedConnectionPort, true))

    // Override this method to customize testing port
    protected def embedConnectionPort: Int = 12345

    // Override this method to personalize MongoDB version
    protected def embedMongoDBVersion: Version = Version.V2_3_0

    protected def mongoStart(): MongodProcess = mongoExe.start()

    protected def mongoStop( mongodProcess: MongodProcess) = mongodProcess.stop()
}