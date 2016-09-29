package com.mapreducelite.example

import com.demo.mapreducelite.core.IO.InputOutputFormat.Record
import com.demo.mapreducelite.example.WordCount
import org.junit.{Before, Test}
import org.junit.Assert.assertEquals

/**
 * Created by chenxu on 26.09.16.
 */
class WordCountTest {

  var wordcount:WordCount = null

  @Before def prepare(): Unit ={
    wordcount = new WordCount()
  }

  @Test def reduceTest(): Unit ={
    val num: List[String] = List("1", "2", "3")
    val result = wordcount.reduce("5",num).asInstanceOf[Record[String,Int]].value
    //assertEquals(wordcount.reduce("5",num), new Record[String,]()

    assertEquals(result, 6)
  }

}
