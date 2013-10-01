resolvers += Resolver.url("sbt-plugin-releases", new URL("http://scalasbt.artifactoryonline.com/scalasbt/sbt-plugin-releases"))(Resolver.ivyStylePatterns)

addSbtPlugin("com.typesafe.sbt" % "sbt-pgp" % "0.7")

addSbtPlugin("com.github.mpeltonen" % "sbt-idea" % "1.1.0")

resolvers += Classpaths.typesafeResolver

addSbtPlugin("com.github.scct" % "sbt-scct" % "0.2")
