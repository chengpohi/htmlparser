/**
  * chengpohi@gmail.com
  */
package com.github.chengpohi.api.dsl

import org.jsoup.nodes.Document

/**
  * htmlparser
  * Created by chengpohi on 15/10/2016.
  */
trait HtmlParserBase {
  val doc: Document
}

trait Definition {
  var key: String = "key"
  var _text: Boolean = false
  var _links: Boolean = false
  def execute: Map[String, Any]
  def as(k: String): Definition = {
    key = k
    this
  }

  def text: Definition = {
    _text = true
    this
  }
  def link: Definition = {
    _links = true
    this
  }
}

trait HtmlParserDefinition extends HtmlParserBase {
  case class IdDefinition(id: String) extends Definition {
    override def execute: Map[String, Any] = {
      val text: String = doc.getElementById(id).text()
      Map(key -> text)
    }
  }
  case class AttrDefinition(k: (String, String)) extends Definition {
    override def execute: Map[String, String] = {
      val text: String = doc.getElementsByAttributeValue(k._1, k._2).text()
      Map(key -> text)
    }
  }
}

object DSL {
  def apply(d: Definition): Map[String, Any] = d.execute
}
