package org.scalabridge.sitegen

import cats.syntax.all._
import org.scalabridge._
import domain.model._
import eu.timepit.refined.types.string.NonEmptyString
import parsley._
import parsley.Parsley.{atomic, many}
import parsley.character.{char, newline, satisfy, space}

object StaticSiteGenerator {
  private val ws: Parsley[Unit] = Parsley.many(space).void

  private val notEol: Parsley[List[Char]] = many(satisfy(_ != '\n'))
  private val h1parser: Parsley[H1] =
    (char('#') ~> ws ~> notEol <~ newline).map(chars =>
      H1(NonEmptyString.unsafeFrom(chars.mkString))
    )
  private val notSquareBracket: Parsley[List[Char]] = many(satisfy(_ != ']'))
  private val notRoundBracket: Parsley[List[Char]] = many(satisfy(_ != ')'))
  private val linkTextParser: Parsley[String] =
    (char('[') ~> notSquareBracket <~ char(']')).map(_.mkString)
  private val linkUrlParser: Parsley[String] =
    (char('(') ~> notRoundBracket <~ char(')')).map(_.mkString)
  private val linkParser: Parsley[Link] = (linkTextParser <~> linkUrlParser)
    .map { case (text, url) =>
      Link(text = NonEmptyString.unsafeFrom(text), url = NonEmptyString.unsafeFrom(url))
    }
  private val parser: Parsley[AST] = atomic(h1parser) <|> linkParser
  private val manyParser: Parsley[List[AST]] = many(parser)

//  private val parser: Parsley[AST] = for {
//    ast <- astParser
//    value <- NonEmptyString.from(in.mkString) match {
//      case Right(v) => Parsley.pure(v)
//      case _        => Parsley.empty
//    }
//  } yield mkNode(value, "h1")

  def parse(markdown: String): Either[Error, AST] =
    parser.parse(markdown).toEither.leftMap(Error.apply)

  def parseMany(markdown: String): Either[Error, List[AST]] =
    manyParser.parse(markdown).toEither.leftMap(Error.apply)

  def generateHtml(tree: AST): HTML = tree match {
    case H1(title)                => H1Html(title)
    case H2(value)                => ??? // mkHtml(value, "h2")
    case H3(value)                => ??? // mkHtml(value, "h3")
    case Bold(value)              => ??? // mkHtml(value, "strong")
    case Italic(value)            => ??? // mkHtml(value, "em")
    case Link(text, url)          => LinkHtml(text = text, url = url)
    case Underlined(value)        => ??? // mkHtml(value, "u")
    case Paragraph(value)         => ??? // mkHtml(value, "p")
    case UnorderedListItem(value) => ??? // mkHtml(value, "ul-li")
    case OrderedListItem(value)   => ??? // mkHtml(value, "ol-li")
  }

  def generateHtml(trees: List[AST]): List[HTML] = trees.map(generateHtml)
}
