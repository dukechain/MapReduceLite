package com.demo.mapreducelite.core.IO.InputOutputFormat

/**
 * Created by chenxu on 27.09.16.
 */
class Record[K, V](val key: K, val value: V){

  def print() {
    println(key+ "," + value)
  }
}

