package com.dataalgorithm.ch1

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkContext, SparkConf}

/**
 *
 * @author jma
 */
object SecondarySort {
  def main(args: Array[String]): Unit = {
    // $SPARK_HOME/bin/spark-submit --class "com.dataalgorithm.ch1.SecondarySort"
    // --master local[2] target/scala-2.10/data-algorithm-scala-assembly-1.0.jar
    // file:///Users/jma/work/data-algorithm-scala/data/ch1/sample_input.txt
    if (args.length < 1) {
      System.err.println(s"Usage: SecondarySort <path>")
      System.exit(1)
    }
    // path = "file:///Users/jma/work/data-algorithm-scala/data/ch1/sample_input.txt"
    val path = args(0)
    val conf = new SparkConf().setAppName("SecondarySort")
    val sc = new SparkContext(conf)
    val linesRDD = sc.textFile(path, 1)
    val groupedRDD = linesRDD.map { line =>
      val tokens = line.split(",")
      val year = tokens(0)
      val month = tokens(1)
      val temperature = tokens(3)
      (year + "-" + month, temperature.toInt)
    }.groupByKey().mapValues(it => it.toSeq.sorted)


    groupedRDD.collect().foreach {
      case (key, seq) => println(key + ": " + seq.mkString(","))
    }

  }

}
