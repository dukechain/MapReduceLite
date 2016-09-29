package com.demo.mapreducelite.example

import com.demo.mapreducelite.core.IO.InputOutputFormat.Record
import com.demo.mapreducelite.runtime.task.MapReduceFunction


import scala.collection.mutable.ListBuffer

/**
 * Created by chenxu on 26.09.16.
 */
class WordCount extends MapReduceFunction{

  override def map(input:Object):List[Object] = {
    var out = ListBuffer.empty[Record[String, Int]]

    def mapandappend(s: String): Unit = {
      var item = new Record[String, Int](s, 1)
      out += item
    }

    input.asInstanceOf[Record[Int,String]].value.split(" ").foreach {
      s => mapandappend(s)
    }

    out.toList
  }

  override def reduce(key:Object, collection: List[Object]): Object = {
    var newlist = collection.asInstanceOf[List[String]].map(x=>x.toInt)
    val sum = newlist.sum

    new Record[String, Int](key.toString, sum)
  }
}
