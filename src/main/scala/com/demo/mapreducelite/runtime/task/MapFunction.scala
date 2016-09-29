package com.demo.mapreducelite.runtime.task

/**
 * Created by chenxu on 26.09.16.
 */
abstract class MapReduceFunction {

  //def map(in: Pair[Int, String]): List[Pair[String, Int]]

  def map(mapinput: Object): List[Object]

  def reduce(key: Object, collection: List[Object]): Object

  //def map(key:Object, value:Object):List[Object]
}
