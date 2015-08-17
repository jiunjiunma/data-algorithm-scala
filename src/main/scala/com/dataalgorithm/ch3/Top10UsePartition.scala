package com.dataalgorithm.ch3

import scala.collection.mutable
import org.apache.spark.broadcast.Broadcast
import org.apache.spark.{SparkContext, SparkConf}

/**
 * An over complicated way to implement the top 10 in Spark.
 *
 * @author jma
 */
object Top10UsePartition {
  implicit val pairCompare = Ordering.by[(String, Int), Int](-_._2)

  def main(args: Array[String]): Unit = {
    // $SPARK_HOME/bin/spark-submit --class "com.dataalgorithm.ch3.Top10UsePartition"
    // --master local[2] target/scala-2.11/data-algorithm-scala-assembly-1.0.jar
    // file:///Users/jma/work/data-algorithm-scala/data/ch3/sample_unique_input.txt
    if (args.length < 1) {
      System.err.println(s"Usage: Top10 <path>")
      System.exit(1)
    }
    val path = args(0)
    val conf = new SparkConf().setAppName("Top10UsePartition")
    val sc = new SparkContext(conf)
    val linesRDD = sc.textFile(path, 1)
    val topN = 10
    val broadcast = sc.broadcast(topN)

    val partitionRDD = linesRDD.mapPartitions { iter =>
      val top10 = new mutable.PriorityQueue[(String, Int)]()
      val topCount = broadcast.value
      iter.foreach { line =>
        val words = line.split("\\s")
        val datum = (words(0), words(1).toInt)
        top10.enqueue(datum)
        if (top10.size > topCount) top10.dequeue()
      }
      top10.iterator
    }

    val finalTop10 = new mutable.PriorityQueue[(String, Int)]()
    partitionRDD.collect().foreach { datum =>
      finalTop10.enqueue(datum)
      if (finalTop10.size > topN) finalTop10.dequeue()
    }

    finalTop10.foreach(println)
  }

}
