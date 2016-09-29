package scalaZip

import java.io.{File, FileInputStream, FileOutputStream}
import java.util.zip.{ZipEntry, ZipOutputStream}

/**
  * Created by osocron on 6/28/16.
  */
object main extends App {

  def createZip() = {
    val fos = new FileOutputStream("src/myzip.zip")
    val zos = new ZipOutputStream(fos)

    val fileName = "src/myText.txt"

    addToZipFile(fileName, zos)
  }

  def addToZipFile(fileName: String, zos: ZipOutputStream) = {
    val file = new File(fileName)
    println(file.exists())
    val fis = new FileInputStream(file)
    val zipEntry = new ZipEntry(fileName)
    zos.putNextEntry(zipEntry)

    val bytes = new Array[Byte](1024)
    var length = fis.read(bytes)
    while(length >= 0) {
      zos.write(bytes, 0, length)
      length = fis.read(bytes)
    }
    zos.closeEntry()
    fis.close()
  }

  createZip()

}
