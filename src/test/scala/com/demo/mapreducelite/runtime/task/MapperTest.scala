package com.demo.mapreducelite.runtime.task

import java.io.File
import java.util

import com.demo.mapreducelite.core.IO.InputOutputFormat.RecordFormat.RecordFormat
import com.demo.mapreducelite.core.Util.FileUtil
import com.demo.mapreducelite.core.config.GlobalConfiguration
import com.demo.mapreducelite.core.config.LocalConfiguration
import com.demo.mapreducelite.runtime.JobConfiguration
import org.junit.{Test, Before}

/**
 * Created by chenxu on 26.09.16.
 */
class MapperTest {

  var jobConfig:JobConfiguration = null
  var mapperTest:Mapper = null

  @Before def prepare() {
    val f: File = new File(LocalConfiguration.dir_main_mapreducelite)
    if (f.exists) {
      FileUtil.deleteDir(f)
    }

    jobConfig = new JobConfiguration()

    jobConfig.mainClassName = "com.example.WordCount"

    var url = classOf[MapperTest].getClassLoader.getResource("").getPath();

    jobConfig.inputFile = new File(url+"text.txt")
    jobConfig.inputFormat = new RecordFormat[Int,String]

    jobConfig.intermediate_outputFormat = new RecordFormat[String, Int]

    jobConfig.jarFileLocation = new File(url+"wordcount.jar")


    mapperTest = new Mapper(jobConfig,System.currentTimeMillis())

    GlobalConfiguration.slave_address_list = new util.ArrayList[String]()
    GlobalConfiguration.slave_address_list.add("localhost")
    GlobalConfiguration.slave_address_list.add("127.0.0.1")

    jobConfig.numofReduceTasks=2
  }

  @Test def runTest(): Unit ={
    mapperTest.run()
  }
}
