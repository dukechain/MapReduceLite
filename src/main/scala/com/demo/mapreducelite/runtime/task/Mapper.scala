package com.demo.mapreducelite.runtime.task

import java.io.File

import com.demo.mapreducelite.core.IO.InputOutputFormat.{OutputWriter, Record, InputFormat, OutputFormat}
import com.demo.mapreducelite.core.Util.JarLoader
import com.demo.mapreducelite.core.config.{GlobalConfiguration, LocalConfiguration}
import com.demo.mapreducelite.runtime.JobConfiguration

import scala.collection.mutable.ListBuffer

/**
 * Created by chenxu on 24.09.16.
 */
class Mapper extends Runnable {

  var mapper_id: Long = 0
  var jobconfig: JobConfiguration = null

  var directory_mapoutput = ""

  def this(jobConfiguration: JobConfiguration, mapper_id: Long) {
    this()
    jobconfig = jobConfiguration
    this.mapper_id = mapper_id
    directory_mapoutput = LocalConfiguration.dir_ShuffleSender + File.separator + jobconfig.jobID + File.separator
  }

  def mapper(inputfile: File, inputFormat: InputFormat, mapFunction: Object, intermediate_outputFormat: OutputFormat): Unit = {

    /*val dir_mapoutput = LocalConfiguration.dir_ShuffleSender + File.separator + jobconfig.jobID + File.separator
    var inter_outputDir = new File(dir_mapoutput)

    if (!inter_outputDir.exists()) {
      inter_outputDir.mkdirs()
    }*/

    var intermediate_writer_list = new ListBuffer[OutputWriter]

    for (i <- 1 to jobconfig.numofReduceTasks) {
      var inter_writer = intermediate_outputFormat.getDataWrite()

      val dir_mapoutput = directory_mapoutput + GlobalConfiguration.slave_address_list.get(i - 1)
      var inter_outputDir = new File(dir_mapoutput)

      if (!inter_outputDir.exists()) {
        inter_outputDir.mkdirs()
      }

      val intermediate_outputfile = new File(dir_mapoutput + File.separator + mapper_id)

      inter_writer.open(intermediate_outputfile)

      intermediate_writer_list += inter_writer
    }

    inputFormat.getDataReader().read(inputfile)
      .foreach(x => {
      var output = mapFunction.asInstanceOf[MapReduceFunction].map(x)

      output.foreach(item => {
        var record = item.asInstanceOf[Record[Object, Object]]

        var key = record.key.asInstanceOf[String]

        var slave_index = jobconfig.partition.hashBucket(key, jobconfig.numofReduceTasks)

        //System.out.println(key + "   " + jobconfig.partition.hashCode(key) + "   " + slave_index)

        intermediate_writer_list(slave_index).write(record)
      }
      )
    }
      )

    intermediate_writer_list.foreach(x => x.close())
  }

  override def run(): Unit = {
    val loadedclass = JarLoader.classloader(jobconfig.getJarFileLocation(), jobconfig.getMainClassName())

    mapper(jobconfig.inputFile, jobconfig.inputFormat, loadedclass.newInstance().asInstanceOf[Object], jobconfig.intermediate_outputFormat)
  }
}
