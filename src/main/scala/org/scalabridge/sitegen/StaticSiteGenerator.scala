package org.scalabridge.sitegen

import cats.syntax.all._
import eu.timepit.refined.types.string.NonEmptyString
import org.scalabridge._
import org.scalabridge.sitegen.domain.model._
import parsley.Parsley.{atomic, many}
import parsley._
import parsley.character._
import parsley.combinator.manyTill

object StaticSiteGenerator {
  private val ws: Parsley[Unit] = Parsley.many(space).void

  val h1Parser: Parsley[H1] = for {
    in <- char('#') ~> ws ~> many(satisfy(_ != '\n')) <~ newline
    value <- NonEmptyString.from(in.mkString) match {
      case Right(v) => Parsley.pure(v)
      case _        => Parsley.empty
    }
  } yield H1(value)

  val underLinedParser: Parsley[Underlined] = for {
    in <- string("__") ~> manyTill(item, string("__")) <~ newline
    value <- NonEmptyString.from(in.mkString) match {
      case Right(v) => Parsley.pure(v)
      case _        => Parsley.empty
    }
  } yield Underlined(value)

  private val notSquareBracket: Parsley[List[Char]] = many(satisfy(_ != ']'))
  private val notRoundBracket: Parsley[List[Char]] = many(satisfy(_ != ')'))
  private val linkTextParser: Parsley[String] =
    (char('[') ~> notSquareBracket <~ char(']')).map(_.mkString)
  private val linkUrlParser: Parsley[String] =
    (char('(') ~> notRoundBracket <~ char(')')).map(_.mkString)
  val linkParser: Parsley[Link] = (linkTextParser <~> linkUrlParser)
    .map { case (text, url) =>
      Link(text = NonEmptyString.unsafeFrom(text), url = NonEmptyString.unsafeFrom(url))
    }

  private val parser: Parsley[AST] = atomic(atomic(h1Parser) <|> underLinedParser) <|> linkParser
  private val manyParser: Parsley[List[AST]] = many(parser)

  def parse(markdown: String, parsleyInstance: Parsley[AST]): Either[Error, AST] =
    parsleyInstance.parse(markdown).toEither.leftMap(Error.apply)

  def parseMany(markdown: String): Either[Error, List[AST]] =
    manyParser.parse(markdown).toEither.leftMap(Error.apply)

  def generateHtml(tree: AST): HTML = tree match {
    case H1(title)     => mkHtml(title, "h1")
    case H2(value)     => mkHtml(value, "h2")
    case H3(value)     => mkHtml(value, "h3")
    case Bold(value)   => mkHtml(value, "strong")
    case Italic(value) => mkHtml(value, "em")
//    case Link(value)              => mkHtml(value, "a")
    case Link(text, url)          => LinkHtml(text = text, url = url)
    case Underlined(value)        => mkHtml(value, "u")
    case Paragraph(value)         => mkHtml(value, "p")
    case UnorderedListItem(value) => mkHtml(value, "ul-li")
    case OrderedListItem(value)   => mkHtml(value, "ol-li")
  }

  def generateHtml(trees: List[AST]): List[HTML] = trees.map(generateHtml)
}
