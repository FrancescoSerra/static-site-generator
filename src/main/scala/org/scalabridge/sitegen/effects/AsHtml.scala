package org.scalabridge.sitegen.effects

import org.scalabridge.sitegen.domain.model.HTML

trait AsHtml[A]:
  def asHtml(a: A): HTML

object AsHtml:
  def apply[A: AsHtml]: AsHtml[A] = summon[AsHtml[A]]
