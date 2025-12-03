package org.scalabridge.sitegen.domain

import eu.timepit.refined.api.Refined
import eu.timepit.refined.string.Url
import eu.timepit.refined.types.all.*
import eu.timepit.refined.auto.*
import eu.timepit.refined.api.*
import org.scalabridge.sitegen.effects.AsHtml

object model {

  type URLString = String Refined Url
  object URLString extends RefinedTypeOps[URLString, String]

  // AST definitions
  sealed trait AST
  final case class H1(value: NonEmptyString) extends AST
  object H1:
    given AsHtml[H1] with
      def asHtml(a: H1): HTML = H1Html(a.value)

  final case class H2(value: NonEmptyString) extends AST
  object H2:
    given AsHtml[H2] with
      def asHtml(a: H2): HTML = H2Html(a.value)

  final case class H3(value: NonEmptyString) extends AST
  object H3:
    given AsHtml[H3] with
      def asHtml(a: H3): HTML = H3Html(a.value)

  final case class Bold(value: NonEmptyString) extends AST
  object Bold:
    given AsHtml[Bold] with
      def asHtml(a: Bold): HTML = BoldHtml(a.value)

  final case class Italic(value: NonEmptyString) extends AST
  object Italic:
    given AsHtml[Italic] with
      def asHtml(a: Italic): HTML = ItalicHtml(a.value)

  final case class Link(text: NonEmptyString, url: URLString) extends AST
  object Link:
    given AsHtml[Link] with
      def asHtml(a: Link): HTML = LinkHtml(a.text, a.url)

  final case class Underlined(value: NonEmptyString) extends AST
  object Underlined:
    given AsHtml[Underlined] with
      def asHtml(a: Underlined): HTML = UnderlinedHtml(a.value)

  final case class Paragraph(value: NonEmptyString) extends AST
  object Paragraph:
    given AsHtml[Paragraph] with
      def asHtml(a: Paragraph): HTML = ParagraphHtml(a.value)

  final case class UnorderedListItem(value: NonEmptyString) extends AST
  object UnorderedListItem:
    given AsHtml[UnorderedListItem] with
      def asHtml(a: UnorderedListItem): HTML = UnorderedListItemHtml(a.value)

  final case class OrderedListItem(value: NonEmptyString) extends AST
  object OrderedListItem:
    given AsHtml[OrderedListItem] with
      def asHtml(a: OrderedListItem): HTML = OrderedListItemHtml(a.value)

  // HTML definitions
  sealed trait HTML {
    def render: String
  }
  final case class H1Html(value: NonEmptyString) extends HTML {
    override def render: String = s"<h1>$value</h1>"
  }
  final case class H2Html(value: NonEmptyString) extends HTML {
    override def render: String = s"<h2>$value</h1>"
  }
  final case class H3Html(value: NonEmptyString) extends HTML {
    override def render: String = s"<h3>$value</h1>"
  }
  final case class BoldHtml(value: NonEmptyString) extends HTML {
    override def render: String = s"<strong>$value</strong>"
  }
  final case class ItalicHtml(value: NonEmptyString) extends HTML {
    override def render: String = s"<em>$value</em>"
  }
  final case class LinkHtml(text: NonEmptyString, url: URLString) extends HTML {
    override def render: String = s"""<a href="$url">$text</a>"""
  }
  final case class UnderlinedHtml(value: NonEmptyString) extends HTML {
    override def render: String = s"<u>$value</u>"
  }
  final case class ParagraphHtml(value: NonEmptyString) extends HTML {
    override def render: String = s"<p>$value</p>"
  }
  final case class UnorderedListItemHtml(value: NonEmptyString) extends HTML {
    override def render: String = s"<ul><li>$value</li></ul>"
  }
  final case class OrderedListItemHtml(value: NonEmptyString) extends HTML {
    override def render: String = s"<ol><li>$value</li></ol>"
  }
}
