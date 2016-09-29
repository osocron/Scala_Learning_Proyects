package com.sisco.osocron

import net.ruippeixotog.scalascraper.browser.JsoupBrowser
import net.ruippeixotog.scalascraper.dsl.DSL._
import net.ruippeixotog.scalascraper.dsl.DSL.Extract._
import net.ruippeixotog.scalascraper.dsl.DSL.Parse._
import net.ruippeixotog.scalascraper.model.Element
/**
  * Created by osocron on 4/6/16.
  */
object Crawler extends App{

  override def main(args: Array[String]) {
    val browser = JsoupBrowser()
    val doc = browser.get("https://www.uv.mx")
    val items = doc >> elementList("a")
    items.foreach(elem => println(elem >?> element("href")))
  }

}
