package com.dataalgorithm.ch3

import scala.collection.mutable

/**
 *
 * @author jma
 */
object Topic10Basic {
  val testData = Map(("k1", 23), ("k2", 45), ("k3", 88), ("k4", 982), ("k5", 333), ("k6", 13), ("K7", 5),
    ("k8", 9862), ("k9", 322), ("k10", 88888), ("k11", 13), ("k12", 971), ("k13", 18))

  // PairCompare has priority over the implicit val pairCompare
  //implicit object PairCompare extends Ordering[(String, Int)] {
  //  override def compare(p1: (String, Int), p2: (String, Int)): Int = { println("xxx"); p2._2 compare p1._2 }
  //}

  implicit val pairCompare = Ordering.by[(String, Int), Int](-_._2)

  def main(args: Array[String]): Unit = {
    val top10 = new mutable.PriorityQueue[(String, Int)]()  //will use implicit (pairCompare)
    testData.foreach { datum =>
      top10.enqueue(datum)
      if (top10.size > 10) top10.dequeue()
    }
    println(top10)

  }

}
