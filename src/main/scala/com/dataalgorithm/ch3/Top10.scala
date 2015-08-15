package com.dataalgorithm.ch3

import org.apache.spark.{SparkContext, SparkConf}

/**
 * Find the top 10 elements, will merge values of duplicate keys.
 *
 * @author jma
 */
object Top10 {
  implicit val pairCompare = Ordering.by[(String, Int), Int](_._2)

  def main(args: Array[String]): Unit = {
    // $SPARK_HOME/bin/spark-submit --class "com.dataalgorithm.ch3.Top10"
    // --master local[2] target/scala-2.11/data-algorithm-scala-assembly-1.0.jar
    // file:///Users/jma/work/data-algorithm-scala/data/ch3/sample_input.txt
    if (args.length < 1) {
      System.err.println(s"Usage: Top10 <path>")
      System.exit(1)
    }
    val path = args(0)
    val conf = new SparkConf().setAppName("Top10")
    val sc = new SparkContext(conf)
    val linesRDD = sc.textFile(path, 1)
    val mappedRDD = linesRDD.map { line =>
      val words = line.split("\\s")
      (words(0), words(1).toInt)
    }.reduceByKey(_+_)

    mappedRDD.top(10).foreach(println)

    // output
    /*
    (k10,88888)
    (k8,9862)
    (k12,988)
    (k4,982)
    (k5,333)
    (k9,322)
    (k3,88)
    (k2,45)
    (k1,23)
    (k13,18)
     */
  }

}
