package org.scalabridge.sitegen

import cats.syntax.all._
import org.scalabridge._
import io.github.iltotore.iron.*
import parsley.Parsley.many
import parsley._
import parsley.character._
import parsley.combinator.manyTill
import org.scalabridge.sitegen.domain.model.*

object StaticSiteGenerator {
  private val ws: Parsley[Unit] = Parsley.many(space).void

  val h1Parser: Parsley[H1] = for {
    in <- char('#') ~> ws ~> many(satisfy(_ != '\n')) <~ newline
    value <- in.mkString.refineEither[NonEmptyString] match {
      case Left(value) => Parsley.empty
      case Right(value) => Parsley.pure(value)
    }
  } yield H1(value)

  val underLinedParser: Parsley[Underlined] = for {
    in <- string("__") ~> manyTill(item, string("__")) <~ newline
    value <- in.mkString.refineEither[NonEmptyString] match {
      case Left(value) => Parsley.empty
      case Right(value) => Parsley.pure(value)
    }
  } yield Underlined(value)

  def parse(markdown: String, parsleyInstance: Parsley[AST]): Either[Error, AST] =
    parsleyInstance.parse(markdown).toEither.leftMap(Error.apply)

  def generateHtml(tree: AST): HTML = tree match {
    case H1(title)                => mkHtml(title, "h1")
    case H2(value)                => mkHtml(value, "h2")
    case H3(value)                => mkHtml(value, "h3")
    case Bold(value)              => mkHtml(value, "strong")
    case Italic(value)            => mkHtml(value, "em")
    case Link(value)              => mkHtml(value, "a")
    case Underlined(value)        => mkHtml(value, "u")
    case Paragraph(value)         => mkHtml(value, "p")
    case UnorderedListItem(value) => mkHtml(value, "ul-li")
    case OrderedListItem(value)   => mkHtml(value, "ol-li")
  }
}
