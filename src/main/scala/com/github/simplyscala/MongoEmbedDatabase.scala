package com.github.simplyscala

import de.flapdoodle.embed.mongo.distribution.Version
import de.flapdoodle.embed.mongo.{Command, MongodProcess, MongodExecutable, MongodStarter}
import de.flapdoodle.embed.mongo.config.{Net, MongodConfigBuilder}
import de.flapdoodle.embed.process.runtime.Network
import de.flapdoodle.embed.process.config.IRuntimeConfig
import de.flapdoodle.embed.process.config.io.ProcessOutput
import de.flapdoodle.embed.mongo.config.RuntimeConfigBuilder


/**
 * Extends this trait provide to your test class a connection to embedMongo database
 */
trait MongoEmbedDatabase {

    private val runtimeConfig = new RuntimeConfigBuilder()
        .defaults(Command.MongoD)
        .processOutput(ProcessOutput.getDefaultInstanceSilent())
        .build()

    protected def mongoStart(port: Int = 12345,
                             version: Version = Version.V3_0_5,
                             runtimeConfig: IRuntimeConfig = runtimeConfig): MongodProps = {
        val mongodExe: MongodExecutable = mongodExec(port, version, runtimeConfig)
        MongodProps(mongodExe.start(), mongodExe)
    }

    protected def mongoStop( mongodProps: MongodProps ) = {
        Option(mongodProps).foreach( _.mongodProcess.stop() )
        Option(mongodProps).foreach( _.mongodExe.stop() )
    }

    protected def withEmbedMongoFixture(port: Int = 12345,
                                        version: Version = Version.V3_0_5,
                                        runtimeConfig: IRuntimeConfig = runtimeConfig)
                                       (fixture: MongodProps => Any) {
        val mongodProps = mongoStart(port, version, runtimeConfig)
        try { fixture(mongodProps) } finally { Option(mongodProps).foreach( mongoStop ) }
    }

    private def runtime(config: IRuntimeConfig): MongodStarter = MongodStarter.getInstance(config)

    private def mongodExec(port: Int, version: Version, runtimeConfig: IRuntimeConfig): MongodExecutable =
        runtime(runtimeConfig).prepare(
            new MongodConfigBuilder()
                .version(version)
                .net(new Net(port, Network.localhostIsIPv6()))
                .build()
        )
}

sealed case class MongodProps(mongodProcess: MongodProcess, mongodExe: MongodExecutable)