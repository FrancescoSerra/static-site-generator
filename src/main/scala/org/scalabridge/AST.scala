package org.scalabridge

sealed trait AST
object AST {
  case class H1(title: String) extends AST
  //...
}
