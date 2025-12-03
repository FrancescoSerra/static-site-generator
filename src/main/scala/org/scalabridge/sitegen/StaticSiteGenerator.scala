package org.scalabridge.sitegen

import cats.syntax.all.*
import eu.timepit.refined.types.string.NonEmptyString
import org.scalabridge.*
import org.scalabridge.sitegen.domain.model.*
import org.scalabridge.sitegen.effects.AsHtml
import org.scalabridge.sitegen.effects.syntax.*
import parsley.Parsley.many
import parsley.*
import parsley.character.*
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
  val linkParser: Parsley[Link] = for {
    (text, url) <- linkTextParser <~> linkUrlParser
    textValue <- NonEmptyString.from(text.mkString) match {
      case Right(v) => Parsley.pure(v)
      case _        => Parsley.empty
    }
    urlValue <- URLString.from(url.mkString) match {
      case Right(v) => Parsley.pure(v)
      case _        => Parsley.empty
    }
  } yield Link(text = textValue, url = urlValue)

  def parse[A <: AST](markdown: String, parsleyInstance: Parsley[A]): Either[Error, A] =
    parsleyInstance.parse(markdown).toEither.leftMap(Error.apply)

  def generateHtml[A <: AST: AsHtml](tree: A): HTML = tree.asHtml
}
