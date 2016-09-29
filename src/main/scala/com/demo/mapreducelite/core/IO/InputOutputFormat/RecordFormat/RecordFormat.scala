package com.demo.mapreducelite.core.IO.InputOutputFormat.RecordFormat

import java.io.File

import com.demo.mapreducelite.core.IO.InputOutputFormat.{OutputWriter, InputReader, InputFormat, OutputFormat}

/**
 * Created by chenxu on 24.09.16.
 *
 * http://stackoverflow.com/questions/8208179/scala-obtaining-a-class-object-from-a-generic-type
 */
class RecordFormat [K,V] extends InputFormat with OutputFormat{

  override def getDataReader():InputReader ={
    return new RecordReader[K,V]();
  }

  override def getDataWrite(): OutputWriter = {
    return new RecordWriter[K,V]();
  }



  /*override def getValueType(): Class[_] = {
    Class[V]
  }*/
}




