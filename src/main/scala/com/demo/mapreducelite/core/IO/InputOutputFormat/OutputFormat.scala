package com.demo.mapreducelite.core.IO.InputOutputFormat

/**
 * Created by chenxu on 24.09.16.
 */
trait OutputFormat {
  def getDataWrite():OutputWriter
}
