package com.dataalgorithm.ch6

import org.apache.spark.{Partitioner, SparkContext, SparkConf}

/**
 *
 * @author jma
 */
object RollingAveragePartition {
  def main(args: Array[String]): Unit = {
    // $SPARK_HOME/bin/spark-submit --class "com.dataalgorithm.ch6.RollingAveragePartition"
    // --master local[2] target/scala-2.10/data-algorithm-scala-assembly-1.0.jar
    // file:///Users/jma/work/data-algorithm-scala/data/ch6/stock.txt
    if (args.length < 1) {
      System.err.println(s"Usage: RollingAveragePartition <path>")
      System.exit(1)
    }
    // path = "file:///Users/jma/work/data-algorithm-scala/data/ch6/stock.txt"
    val path = args(0)
    val conf = new SparkConf().setAppName("RollingAveragePartition")
    val sc = new SparkContext(conf)
    val linesRDD = sc.textFile(path, 1)
    val partitionedRDD = linesRDD.map { line =>
      val tokens = line.split(",")
      val symbol = tokens(0)
      val date = tokens(1)
      val value = tokens(2)
      ((symbol, date), value.toDouble)
    }.repartitionAndSortWithinPartitions(new Partitioner(){
      override def numPartitions: Int = 2

      override def getPartition(key: Any): Int = key match {
        case (symbol: String, _) => symbol.hashCode % numPartitions
      }
    })


    partitionedRDD.foreachPartition { iter =>
      var rollingAverage: RollingAverage = null
      var previousSymbol = ""
      iter.foreach {
        case ((symbol, date), value) =>
          if (symbol != previousSymbol) rollingAverage = new RollingAverage(4)
          previousSymbol = symbol
          rollingAverage.addDataPoint(value)
          println(s"$symbol $date ${rollingAverage.getAverage}")
      }

    }
  }

  /* output
AAPL 2013-10-03 483.41
AAPL 2013-10-04 483.58000000000004
AAPL 2013-10-04 483.3966666666667
AAPL 2013-10-07 482.7825
AAPL 2013-10-09 483.5775
GOOG 2004-11-02 194.87
GOOG 2004-11-03 193.26999999999998
GOOG 2004-11-04 190.41333333333333
GOOG 2013-07-17 372.4475
GOOG 2013-07-18 551.4
GOOG 2013-07-19 727.6324999999999
IBM 2013-09-25 189.47
IBM 2013-09-27 188.195
IBM 2013-09-30 187.18999999999997
IMB 2013-09-26 190.22
   */

}
