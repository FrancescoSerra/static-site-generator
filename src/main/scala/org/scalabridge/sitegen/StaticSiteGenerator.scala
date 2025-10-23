package org.scalabridge.sitegen

import cats.syntax.all._
import org.scalabridge._
import domain.model._
import eu.timepit.refined.types.string.NonEmptyString
import parsley._
import parsley.Parsley.many
import parsley.character.{char, newline, satisfy, space}

object StaticSiteGenerator {
  private val ws: Parsley[Unit] = Parsley.many(space).void

  private val parser: Parsley[AST] = for {
    in <- (char('#') ~> ws ~> many(satisfy(_ != '\n')) <~ newline)
    value <- NonEmptyString.from(in.mkString) match {
              case Right(v) => Parsley.pure(v)
              case _ => Parsley.empty
            }
  } yield mkNode(value, "h1")

    //...

  def parse(markdown: String): Either[Error, AST] =
    parser.parse(markdown).toEither.leftMap(Error.apply)

  def generateHtml(tree: AST): HTML = tree match {
    case H1(title) => mkHtml(title, "h1")
    case H2(value) => mkHtml(value, "h2")
    case H3(value) => mkHtml(value, "h3")
    case Bold(value) => mkHtml(value, "strong")
    case Italic(value) => mkHtml(value, "em")
    case Link(value) => mkHtml(value, "a")
    case Underlined(value) => mkHtml(value, "u")
    case Paragraph(value) => mkHtml(value, "p")
    case UnorderedListItem(value) => mkHtml(value, "ul-li")
    case OrderedListItem(value) => mkHtml(value, "ol-li")
  }
}
