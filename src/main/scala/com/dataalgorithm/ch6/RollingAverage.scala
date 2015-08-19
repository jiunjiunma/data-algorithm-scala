package com.dataalgorithm.ch6

import scala.collection.mutable.ArrayBuffer

/**
 *
 * @author jma
 */
class RollingAverage(windowSize: Int) {
  private val buffer = new Array[Double](windowSize)
  private var bufferSize = 0
  private var index = 0
  private var total = 0.0

  def addDataPoint(value: Double): Unit = {
    if (bufferSize < windowSize) {
      buffer(index) = value
      bufferSize += 1
    } else {
      if (index == windowSize) index = 0
      total -= buffer(index)
      buffer (index) = value
    }
    index += 1
    total += value
  }

  def getAverage: Double = total / bufferSize

}


