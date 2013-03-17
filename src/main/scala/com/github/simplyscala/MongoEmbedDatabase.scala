package com.github.simplyscala

import de.flapdoodle.embed.mongo.distribution.Version
import de.flapdoodle.embed.mongo.{MongodProcess, MongodExecutable, MongodStarter}
import de.flapdoodle.embed.mongo.config.MongodConfig

/**
 * Extends this trait provide to your test class a connection to embedMongo database
 */
trait MongoEmbedDatabase {

    private def runtime(): MongodStarter = MongodStarter.getDefaultInstance
    private def mongodExec(port: Int, version: Version): MongodExecutable = runtime().prepare(new MongodConfig(version, port, true))

    protected def mongoStart(port: Int = 12345, version: Version = Version.V2_3_0): MongodProps = {
        val mongodExe = mongodExec(port, version)
        MongodProps(mongodExe.start(), mongodExe)
    }

    protected def mongoStop( mongodProps: MongodProps ) = {
        Option(mongodProps).foreach( _.mongodProcess.stop() )
        Option(mongodProps).foreach( _.mongodExe.stop() )
    }

    protected def withEmbedMongoFixture(port: Int = 12345, version: Version = Version.V2_3_0)(fixture: MongodProps => Any) {
        val mayBeMongodProps: Option[MongodProps] = try {
            val mongodProps = mongoStart(port, version)
            fixture(mongodProps)
            Option(mongodProps)
        } catch {
            case _ => None  // TODO rajouter log.error
        }

        mayBeMongodProps.foreach( mongoStop(_) )
    }
}

sealed case class MongodProps(mongodProcess: MongodProcess, mongodExe: MongodExecutable)