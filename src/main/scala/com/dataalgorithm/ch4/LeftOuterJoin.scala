package com.dataalgorithm.ch4

import org.apache.spark.{SparkContext, SparkConf}

/**
 *
 * @author jma
 */
object LeftOuterJoin {
  def main(args: Array[String]): Unit = {
    // $SPARK_HOME/bin/spark-submit --class "com.dataalgorithm.ch4.LeftOuterJoin"
    // --master local[2] target/scala-2.10/data-algorithm-scala-assembly-1.0.jar
    // file:///Users/jma/work/data-algorithm-scala/data/ch4/users.txt
    // file:///Users/jma/work/data-algorithm-scala/data/ch4/transactions.txt
    if (args.length != 2) {
      System.err.println(s"Usage: LeftOuterJoin <users> <transactions>")
      System.exit(1)
    }
    val userPath = args(0)
    val transactionPath = args(1)
    val conf = new SparkConf().setAppName("LeftOuterJoin")
    val sc = new SparkContext(conf)
    val userLocation = sc.textFile(userPath, 1).map { line =>
      val tokens = line.split("\\s")
      // (user, location)
      (tokens(0), tokens(1))
    }
    val productTransaction = sc.textFile(transactionPath, 1).map { line =>
      val tokens = line.split("\\s")
      // (user, product)
      (tokens(2), tokens(1))
    }

    val joined = productTransaction.leftOuterJoin(userLocation).map {
      case (user, (product, Some(location))) => (product, location)
    }.groupByKey().mapValues { iter =>
      val set = new scala.collection.mutable.HashSet[String]()
      iter.foreach(set.add)
      set.size
    }

    joined.collect().foreach(println)



  }

}
