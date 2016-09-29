package com.mapreducelite.runtime.task

import java.io.File
import java.util

import com.demo.mapreducelite.core.IO.InputOutputFormat.RecordFormat.RecordFormat
import com.demo.mapreducelite.core.config.GlobalConfiguration
import com.demo.mapreducelite.runtime.JobConfiguration
import com.demo.mapreducelite.runtime.task.Mapper
import org.junit.{Test, Before}

/**
 * Created by chenxu on 26.09.16.
 */
class MapperTest {

  var jobConfig:JobConfiguration = null
  var mapperTest:Mapper = null

  @Before def prepare() {
    jobConfig = new JobConfiguration()
        jobConfig.mainClassName = "com.demo.mapreducelite.example.WordCount"
    jobConfig.inputFile = new File("./text.txt")
    jobConfig.inputFormat = new RecordFormat[Int,String]

    jobConfig.intermediate_outputFormat = new RecordFormat[String, Int]

    jobConfig.jarFileLocation = new File("./classes/artifacts/wordcount/wordcount.jar")

    mapperTest = new Mapper(jobConfig)

    GlobalConfiguration.slave_address_list = new util.ArrayList[String]()
    GlobalConfiguration.slave_address_list.add("1")
    GlobalConfiguration.slave_address_list.add("2")

    jobConfig.numofReduceTasks=2
  }

  @Test def runTest(): Unit ={
    mapperTest.run()
  }
}
