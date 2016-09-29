package com.mapreducelite.runtime.task

import java.io.File

import com.demo.mapreducelite.core.IO.InputOutputFormat.RecordFormat.RecordFormat
import com.demo.mapreducelite.core.config.LocalConfiguration
import com.demo.mapreducelite.runtime.JobConfiguration
import com.demo.mapreducelite.runtime.task.Reducer
import org.junit.{Test, Before}


/**
 * Created by chenxu on 27.09.16.
 */
class ReducerTest {
  var jobConfig:JobConfiguration = null
  var reducerTest:Reducer = null

  @Before def prepare(): Unit = {
    jobConfig = new JobConfiguration()
    jobConfig.mainClassName = "com.demo.mapreducelite.example.WordCount"
    jobConfig.outputFile = new File("./result.txt")
    jobConfig.outputFormat = new RecordFormat[Int,String]

    jobConfig.intermediate_inputFormat = new RecordFormat[String, Int]

    jobConfig.jarFileLocation = new File("./classes/artifacts/wordcount/wordcount.jar")

    reducerTest = new Reducer(jobConfig)
    reducerTest.inter_inputfile = new File(LocalConfiguration.dir_mapoutput + "out/2")
  }

  @Test def runTest(): Unit ={
    reducerTest.run()
  }
}
