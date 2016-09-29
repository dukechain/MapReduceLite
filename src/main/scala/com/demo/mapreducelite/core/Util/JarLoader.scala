package com.demo.mapreducelite.core.Util

import java.io.File

/**
 * Created by chenxu on 25.09.16.
 */
object JarLoader {

  def classloader(jarfile: File, userdefined_class_name: String): Class[_] = {

    var classLoader = new java.net.URLClassLoader(
      Array(jarfile.toURI.toURL),
      this.getClass.getClassLoader)

    val loaded_class = classLoader.loadClass(userdefined_class_name)

    loaded_class
}

}
