package com.demo.mapreducelite.core.partition

/**
 * Created by chenxu on 28.09.16.
 */
trait HashPartition {

  def hashCode(thiz: String): Int

  def hashBucket(thiz:String, n:Int):Int

}
