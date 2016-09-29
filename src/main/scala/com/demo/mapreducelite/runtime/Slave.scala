package com.demo.mapreducelite.runtime

import java.util.concurrent.Executors

import com.demo.mapreducelite.core.Util.JarLoader

/**
 * Created by chenxu on 24.09.16.
 */
class Slave extends Runnable {
  private val shuffleSenders = Executors.newFixedThreadPool(2)

  private var jobList: List[JobConfiguration] = null

  def run(): Unit = {

    while (true) {

    }

  }

  def main(args: Array[String]) {

  }
}
