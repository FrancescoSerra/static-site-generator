package org.scalabridge.sitegen.effects.syntax

import org.scalabridge.sitegen.domain.model.HTML
import org.scalabridge.sitegen.effects.AsHtml

trait AsHtmlSyntax {
  implicit def toAsHtmlOps[A](a: A): AsHtmlOps[A] = new AsHtmlOps(a)
}

final class AsHtmlOps[A](private val a: A) extends AnyVal {
  def asHtml(using ev: AsHtml[A]): HTML = ev.asHtml(a)
}
