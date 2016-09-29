package com.demo.mapreducelite.core.IO.InputOutputFormat.RecordFormat

import java.io.{FileWriter, BufferedWriter, File}

import com.demo.mapreducelite.core.IO.InputOutputFormat.{Record, OutputWriter}

/**
 * Created by chenxu on 24.09.16.
 */
class RecordWriter [K,V] extends OutputWriter {

  var bw:BufferedWriter = null

  def open(outputFile :File): Unit={
    if(bw == null){
      bw = new BufferedWriter(new FileWriter(outputFile))
    }
  }

  override def write(data: List[Object]): Unit = {
    data.foreach(s =>
        bw.write( s.asInstanceOf[Record[K,V]].key.toString + " " +
          s.asInstanceOf[Record[K,V]].value.toString +
          System.getProperty("line.separator")))
  }

  override def write(s: Object): Unit = {
      bw.write( s.asInstanceOf[Record[K,V]].key.toString + " " +
        s.asInstanceOf[Record[K,V]].value.toString +
        System.getProperty("line.separator"))
  }

  def close():Unit={
    bw.close()
  }

}
