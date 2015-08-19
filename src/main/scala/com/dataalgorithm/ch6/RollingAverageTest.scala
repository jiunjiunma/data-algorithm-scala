package com.dataalgorithm.ch6

/**
 *
 * @author jma
 */
object RollingAverageTest {
  def main(args: Array[String]): Unit = {

    val windowSizes = List( 3, 4)
    val data = List(1.0, 2.0, 3.0, 4.0, 4.0, 5.0, 6.0)

    for (windowSize <- windowSizes ) {
      val rollingAverager = new RollingAverage(windowSize)
      for (value <- data) {
        rollingAverager.addDataPoint(value)
        println(rollingAverager.getAverage)
      }
    }


  }

}
