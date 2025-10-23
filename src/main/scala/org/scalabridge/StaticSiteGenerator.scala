package org.scalabridge

import parsley.Parsley
import parsley.Parsley.many
import parsley.character.{char, newline, satisfy}

object StaticSiteGenerator {
  private val parser: Parsley[AST] =
    (char('#') ~> many(satisfy(_ != '\n')) <~ newline).map(strs => AST.H1(strs.mkString))
    //...

  def parse(markdown: Markdown): Either[Error, AST] =
    parser.parse(markdown.value).toEither.left.map(Error.apply)

  def generateHtml(tree: AST): Html = tree match {
    case AST.H1(title) => Html(s"<h1>$title</h1>")
  }
}
