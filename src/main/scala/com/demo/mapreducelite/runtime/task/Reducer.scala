package com.demo.mapreducelite.runtime.task

import java.io.File

import com.demo.mapreducelite.core.IO.InputOutputFormat.{OutputFormat, Record, InputFormat}
import com.demo.mapreducelite.core.IO.InputOutputFormat.RecordFormat.RecordFormat
import com.demo.mapreducelite.core.Util.JarLoader
import com.demo.mapreducelite.core.config.LocalConfiguration
import com.demo.mapreducelite.runtime.JobConfiguration

import scala.collection.immutable.ListMap
import scala.collection.mutable.{ListBuffer, HashMap}

/**
 * Created by chenxu on 26.09.16.
 */
class Reducer extends Runnable {

  var reduceMap = new HashMap[Object, ListBuffer[Object]]

  var jobconfig: JobConfiguration = null;

  // map -> reduce
  var inter_inputfile = new File(LocalConfiguration.dir_mapoutput + "out/2")

  def this(jobConfiguration: JobConfiguration) {
    this()
    jobconfig = jobConfiguration
  }

  def reducer(outputfile: File, outputFormat: OutputFormat, reduceFunction: Object, intermediate_inputFormat: InputFormat): Unit = {
    //val intermediate_inputFormat = new RecordFormat[String,Int]

    //var intermediate_reader = intermediate_inputFormat.getDataReader()

    //intermediate_reader.read(inter_inputfile)

    intermediate_inputFormat.getDataReader().read(inter_inputfile)
      .foreach(x => {
      var record = x.asInstanceOf[Record[Object, Object]]
      var temp = record.value.toString.split(" ")
      //var output = reduceFunction.asInstanceOf[MapReduceFunction].map(x.asInstanceOf[Object])
      //intermediate_reader

      //var lbuffer = reduceMap.get(temp(0))
      if (reduceMap.contains(temp(0))) {
        var lbuffer = reduceMap.apply(temp(0))

        lbuffer += temp(1)
        System.out.println(lbuffer)

        //reduceMap.put(temp(0), lbuffer)

      } else {
        var ll = new ListBuffer[Object]

        ll += temp(1)
        reduceMap.put(temp(0), ll)
      }
    })

    var sortedMap = ListMap(reduceMap.toSeq.sortBy(_._1.toString):_*)

    var writer = outputFormat.getDataWrite()
    writer.open(outputfile)

    reduceMap.foreach(x=>{
      val result = reduceFunction.asInstanceOf[MapReduceFunction].reduce(x._1, x._2.toList)

      writer.write(result)
    })

    writer.close()

    //System.out.println(reduceMap.apply("China"))
  }

  override def run(): Unit = {
    val loadedclass = JarLoader.classloader(jobconfig.getJarFileLocation(), jobconfig.getMainClassName())

    reducer(jobconfig.outputFile, jobconfig.outputFormat, loadedclass.newInstance().asInstanceOf[Object], jobconfig.intermediate_inputFormat)
  }
}
