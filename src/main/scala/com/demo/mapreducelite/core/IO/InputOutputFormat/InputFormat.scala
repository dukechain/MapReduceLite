package com.demo.mapreducelite.core.IO.InputOutputFormat

import java.io.File

/**
 * Created by chenxu on 24.09.16.
 */
trait InputFormat{

  def getDataReader():InputReader

  //def getKeyType(m: Manifest[_]):Class[_]

  //def getValueType():Class[_]
}
