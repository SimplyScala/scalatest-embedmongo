package com.github.simplyscala

import de.flapdoodle.embed.mongo.distribution.Version
import de.flapdoodle.embed.mongo.{MongodProcess, MongodExecutable, MongodStarter}
import de.flapdoodle.embed.mongo.config.{Net, MongodConfigBuilder}
import de.flapdoodle.embed.process.runtime.Network

/**
 * Extends this trait provide to your test class a connection to embedMongo database
 */
trait MongoEmbedDatabase {

    private def runtime(): MongodStarter = MongodStarter.getDefaultInstance
    private def mongodExec(port: Int, version: Version): MongodExecutable = runtime().prepare(
      new MongodConfigBuilder()
            .version(version)
            .net(new Net(port, Network.localhostIsIPv6()))
            .build())


    protected def mongoStart(port: Int = 12345, version: Version = Version.V2_4_8): MongodProps = {
        val mongodExe = mongodExec(port, version)
        MongodProps(mongodExe.start(), mongodExe)
    }

    protected def mongoStop( mongodProps: MongodProps ) = {
        Option(mongodProps).foreach( _.mongodProcess.stop() )
        Option(mongodProps).foreach( _.mongodExe.stop() )
    }

    protected def withEmbedMongoFixture(port: Int = 12345, version: Version = Version.V2_4_8)(fixture: MongodProps => Any) {
        val mongodProps = mongoStart(port, version)
        try { fixture(mongodProps) } finally { Option(mongodProps).foreach( mongoStop ) }
    }
}

sealed case class MongodProps(mongodProcess: MongodProcess, mongodExe: MongodExecutable)