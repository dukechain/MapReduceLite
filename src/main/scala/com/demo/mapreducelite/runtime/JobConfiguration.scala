package com.demo.mapreducelite.runtime

import java.io.File

import com.demo.mapreducelite.core.IO.InputOutputFormat.{OutputFormat, InputFormat}
import com.demo.mapreducelite.core.partition.{SimpleHashPartition, HashPartition}

/**
 * Created by chenxu on 25.09.16.
 */
class JobConfiguration {
  var jobID = -1L

  var mainClassName = ""

  var jarFileLocation: File = null

  var numofMapTasks = 1
  var numofReduceTasks = 1

  var inputFile: File = null
  var inputFormat: InputFormat = null
  var outputFile: File = null
  var outputFormat: OutputFormat = null

  var intermediate_inputFormat: InputFormat = null
  var intermediate_outputFormat: OutputFormat = null

  var partition: HashPartition = new SimpleHashPartition()


  def setNumofMapTasks(numofMapTask: Int): Unit = {
    this.numofMapTasks = numofMapTasks
  }

  def getJarFileLocation(): File = {
    jarFileLocation
  }

  def getMainClassName(): String = {
    mainClassName
  }
}
