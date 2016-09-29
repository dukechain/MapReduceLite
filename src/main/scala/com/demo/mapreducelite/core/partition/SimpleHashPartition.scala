package com.demo.mapreducelite.core.partition

/**
 * Created by chenxu on 28.09.16.
 */
class SimpleHashPartition extends HashPartition{

  def hashCode(thiz: String): Int = {
    var res = 0
    var mul = 1 // holds pow(31, length-i-1)
    var i = thiz.length-1
    while (i >= 0) {
      res += thiz.charAt(i) * mul
      mul *= 31
      i -= 1
    }
    res
  }

  def hashBucket(thiz:String, n:Int):Int = {
    val hashvalue = hashCode(thiz)

    (Math.abs(hashvalue) % n.toDouble).toInt
  }
}
