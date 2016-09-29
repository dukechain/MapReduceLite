package com.demo.mapreducelite.core.IO.InputOutputFormat.RecordFormat

import java.io.File

import com.demo.mapreducelite.core.IO.InputOutputFormat.{Record, InputReader}

import scala.collection.mutable.ListBuffer
import scala.io.Source

/**
 * Created by chenxu on 24.09.16.
 */
class RecordReader[K, V] extends InputReader {

  //protected var data_collection:List[Record[K,V]] = null


  override def read(inputfile: File): List[Object] = {
      var buffer = ListBuffer.empty[Record[K, V]]
      for (line <- Source.fromFile(inputfile).getLines()) {

        val record = new Record(0.asInstanceOf[K], line.asInstanceOf[V])

        buffer += record
      }

      buffer.toList
    }

  override def open(outputFile: File): Unit = {
    
  }

  override def close(): Unit = {

  }
}
