package com.demo.mapreducelite.runtime.task

import java.io.File

import com.demo.mapreducelite.core.config.{LocalConfiguration, GlobalConfiguration}
import com.demo.mapreducelite.runtime.JobConfiguration
import com.demo.mapreducelite.runtime.shuffle.{ShuffleReceiver, ShuffleSender}

/**
 * Created by chenxu on 29.09.16.
 */
class Shuffler extends Runnable {

  //var sender:ShuffleSender = new ShuffleSender()
  var receiver: ShuffleReceiver = null

  var jobconfig: JobConfiguration = null
  var shuffler_id = -1L

  var shuffler_input_dir:String =null

  def this(jobConfiguration: JobConfiguration, shuffler_id: Long) {
    this()
    jobconfig = jobConfiguration
    this.shuffler_id = shuffler_id

    shuffler_input_dir = LocalConfiguration.dir_ShuffleSender+ jobconfig.jobID
  }

  def startReceiver(): Unit = {
    receiver = new ShuffleReceiver(GlobalConfiguration.port_ShuffleReceiver,
      new File(LocalConfiguration.dir_ShuffleReceiver + jobconfig.jobID+File.separator))

    new Thread(receiver).start()
  }

  def startSender(): Unit = {
    val dir = new File(shuffler_input_dir)
      val dirfiles=dir.listFiles

    val dirList=dirfiles.filter(_.isDirectory)

    dirList.foreach(dir => {

      val files = dir.listFiles.filter(_.isFile)

      val name = dir.getName
      var sender: ShuffleSender = new ShuffleSender(dir.getName, GlobalConfiguration.port_ShuffleReceiver, files)
      new Thread(sender).start()
    })


  }

  override def run(): Unit = {
    startReceiver()
    startSender()
  }
}
