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

  var jobconfig: JobConfiguration = null;

  def this(jobConfiguration: JobConfiguration) {
    this()
    jobconfig = jobConfiguration
  }

  def mapper(inputfile: File, inputFormat: InputFormat, mapFunction: Object, intermediate_outputFormat: OutputFormat): Unit = {

    //val bw = new BufferedWriter(new FileWriter(outputfile))

    //val rf = inputFormat.asInstanceOf[RecordFormat]

    //inputFormat.load(inputfile)

    //val intermediate_outputFormat = new RecordFormat[String,Int]
    var inter_outputDir = new File(LocalConfiguration.dir_mapoutput + "out")

    if (!inter_outputDir.exists()) {
      inter_outputDir.mkdirs()
    }

    var intermediate_writer_list = new ListBuffer[OutputWriter]

    for (i <- 1 to jobconfig.numofReduceTasks) {
      var inter_writer = intermediate_outputFormat.getDataWrite()

      //val intermediate_outputfile = new File(LocalConfiguration.dir_mapoutput + "out")
      //intermediate_writer.open(intermediate_outputfile)
      val intermediate_outputfile = new File(LocalConfiguration.dir_mapoutput +
        "out" + File.separator + GlobalConfiguration.slave_address_list.get(i-1))

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

        System.out.println(key + "   "+jobconfig.partition.hashCode(key)+"   "+slave_index)

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
