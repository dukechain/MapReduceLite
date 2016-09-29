package com.demo.mapreducelite.core.IO.InputOutputFormat

import java.io.File

/**
 * Created by chenxu on 26.09.16.
 */
trait OutputWriter {
  //def write(data: List[T], outputFile:File):Unit

  def open(outputFile:File):Unit

  def write(data: List[Object]):Unit

  def write(data: Object): Unit

  def close():Unit
}
