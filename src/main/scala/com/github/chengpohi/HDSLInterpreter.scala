package com.github.chengpohi

import com.github.chengpohi.html.parser.HDSLParser
import fastparse.core.Parsed.{Failure, Success}
import org.jsoup.nodes.Document

/**
  * Created by xiachen on 24/10/2016.
  */
class HDSLInterpreter(doc: Document) {
  private val hdslParser: HDSLParser = new HDSLParser(doc)

  def intercept(source: String): Map[String, Any] = {
    doc match {
      case null => Map()
      case _ =>
        val parseResult = hdslParser.parse(source.trim)
        parseResult match {
          case Success(f, state) => f.flatMap(_.execute).toMap
          case Failure(_, _, t) => throw new Exception(t.traced.trace)
        }
    }
  }
}
