package com.demo.mapreducelite.runtime.task

import java.io.File

import com.demo.mapreducelite.core.IO.InputOutputFormat.RecordFormat.RecordFormat
import com.demo.mapreducelite.runtime.{Slave, JobConfiguration}
import org.junit.{Test, Before}


/**
 * Created by chenxu on 27.09.16.
 */
class ReducerTest {
  var jobConfig:JobConfiguration = null
  var reducerTest:Reducer = null

  /*def prepareShuffler():Unit ={
    Slave.set_terminated_Receiver(false)

    val f: File = new File(LocalConfiguration.dir_main_mapreducelite)
    if (f.exists) {
      FileUtil.deleteDir(f)
    }

    var shufflertest = new ShufflerTest()
    shufflertest.prepare()
    shufflertest.runTest()

    //Slave.set_terminated_Receiver(true)

    //Thread sleep 10000
  }*/

  @Before def prepare(): Unit = {
    //prepareShuffler()

    jobConfig = new JobConfiguration()
    jobConfig.mainClassName = "com.example.WordCount"

    var url = classOf[ReducerTest].getClassLoader.getResource("").getPath();

    jobConfig.outputFile = new File(url+"result.txt")
    jobConfig.outputFormat = new RecordFormat[Int,String]

    jobConfig.intermediate_inputFormat = new RecordFormat[String, Int]

    jobConfig.jarFileLocation = new File(url+"wordcount.jar")

    reducerTest = new Reducer(jobConfig)

    url = classOf[ShufflerTest].getClassLoader.getResource("./shuffleReceiver").getPath
    reducerTest.reduceinputpath = url

    //reducerTest.inter_inputfile = new File(LocalConfiguration.dir_mapoutput + "out/2")
  }

  @Test def runTest(): Unit ={
    reducerTest.run()


  }
}
