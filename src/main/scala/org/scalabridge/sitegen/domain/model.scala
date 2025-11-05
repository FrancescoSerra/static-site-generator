package org.scalabridge.sitegen.domain

import eu.timepit.refined.types.all.NonEmptyString

object model {

  // AST definitions
  sealed trait AST
  final case class H1(value: NonEmptyString) extends AST
  final case class H2(value: NonEmptyString) extends AST
  final case class H3(value: NonEmptyString) extends AST
  final case class Bold(value: NonEmptyString) extends AST
  final case class Italic(value: NonEmptyString) extends AST
  final case class Link(value: NonEmptyString) extends AST
  final case class Underlined(value: NonEmptyString) extends AST
  final case class Paragraph(value: NonEmptyString) extends AST
  final case class UnorderedListItem(value: NonEmptyString) extends AST
  final case class OrderedListItem(value: NonEmptyString) extends AST

  // naive weak and brittle smart constructor
  def mkNode(value: NonEmptyString, typ: String): AST = typ match {
    case "h1"     => H1(value)
    case "h2"     => H2(value)
    case "h3"     => H3(value)
    case "strong" => Bold(value)
    case "em"     => Italic(value)
    case "a"      => Link(value)
    case "u"      => Underlined(value)
    case "p"      => Paragraph(value)
    case "ul-li"  => UnorderedListItem(value)
    case "ol-li"  => OrderedListItem(value)
  }

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
  final case class LinkHtml(value: NonEmptyString) extends HTML {
    override def render: String = s"<a href=$value>$value</h1>"
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

  // naive, weak and brittle smart constructor
  def mkHtml(value: NonEmptyString, typ: String): HTML = typ match {
    case "h1"     => H1Html(value)
    case "h2"     => H2Html(value)
    case "h3"     => H3Html(value)
    case "strong" => BoldHtml(value)
    case "em"     => ItalicHtml(value)
    case "a"      => LinkHtml(value)
    case "u"      => UnderlinedHtml(value)
    case "p"      => ParagraphHtml(value)
    case "ul-li"  => UnorderedListItemHtml(value)
    case "ol-li"  => OrderedListItemHtml(value)
  }

}
