name := """data-algorithm-scala"""

version := "1.0"

scalaVersion := "2.11.7"

scalacOptions in ThisBuild += "-language:postfixOps"

// Uncomment to use Akka
//libraryDependencies += "com.typesafe.akka" %% "akka-actor" % "2.3.11"
val sparkVersion = "1.4.0"
val json4sVersion = "3.2.10"

libraryDependencies ++= Seq(
  "org.slf4j" % "slf4j-api" % "1.7.10" % "provided",
  "org.apache.spark" %% "spark-core" % sparkVersion % "provided",
  "org.apache.spark" %% "spark-streaming" % sparkVersion % "provided",
  "com.amazonaws" % "aws-java-sdk" % "1.9.16",
  "org.rogach" %% "scallop" % "0.9.5",
  "org.apache.spark" %% "spark-streaming-kinesis-asl" % sparkVersion excludeAll(
    ExclusionRule("com.amazonaws", "aws-java-sdk"),
    ExclusionRule("org.spark-project.spark"),
    ExclusionRule("org.apache.spark", "spark-streaming_2.11"),
    ExclusionRule("com.fasterxml.jackson.core"),
    ExclusionRule("org.apache.httpcomponents", "httpclient"),
    ExclusionRule("commons-logging"),
    ExclusionRule("commons-codec")
    ),
  "org.apache.hadoop" % "hadoop-aws" % "2.6.0" excludeAll(
    ExclusionRule("com.amazonaws", "aws-java-sdk"),
    ExclusionRule("com.fasterxml.jackson.core"),
    ExclusionRule("commons-logging"),
    ExclusionRule("commons-beanutils"),
    ExclusionRule("commons-collections")
    ),
  "org.apache.avro" % "avro" % "1.7.7" % "provided",
  "org.json4s" %% "json4s-ast" % json4sVersion % "provided",
  "org.json4s" %% "json4s-core" % json4sVersion % "provided",
  "org.json4s" %% "json4s-jackson" % json4sVersion % "provided"
)

assemblyOption in assembly := (assemblyOption in assembly).value.copy(includeScala = false)



