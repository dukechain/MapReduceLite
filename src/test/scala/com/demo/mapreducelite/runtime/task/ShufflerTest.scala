package com.demo.mapreducelite.runtime.task

import java.io.File

import com.demo.mapreducelite.core.Util.FileUtil
import com.demo.mapreducelite.core.config.LocalConfiguration
import com.demo.mapreducelite.runtime.{Slave, JobConfiguration}
import org.junit.{Test, Before}

/**
 * Created by chenxu on 30.09.16.
 */
class ShufflerTest {

  var jobConfig:JobConfiguration = null
  var shufflerTest:Shuffler = null

  def prepareMapper():Unit ={
    Slave.set_terminated_Receiver(false)

    val f: File = new File(LocalConfiguration.dir_main_mapreducelite)
    if (f.exists) {
      FileUtil.deleteDir(f)
    }

    var mappertest = new MapperTest()
    mappertest.prepare()
    mappertest.runTest()
  }

  @Before def prepare(): Unit = {
    //prepareMapper()

    jobConfig = new JobConfiguration()

    shufflerTest = new Shuffler(jobConfig, System.currentTimeMillis())

    val url = classOf[ShufflerTest].getClassLoader.getResource("./shuffleSender").getPath
    shufflerTest.shuffler_input_dir = url
  }

  @Test def runTest(): Unit ={
    //val t = new Thread(shufflerTest)
    shufflerTest.run()

    Thread sleep 10000

    Slave.set_terminated_Receiver(true)

    Thread sleep 20000

    //t.interrupt()
  }
}
