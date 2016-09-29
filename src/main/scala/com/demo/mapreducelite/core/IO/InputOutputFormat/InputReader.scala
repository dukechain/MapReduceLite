package com.demo.mapreducelite.core.IO.InputOutputFormat

import java.io.File



/**
 * Created by chenxu on 24.09.16.
 */
trait InputReader {

  def open(outputFile:File):Unit

  def read (inputfile: File): List[Object]

  def close():Unit
}
