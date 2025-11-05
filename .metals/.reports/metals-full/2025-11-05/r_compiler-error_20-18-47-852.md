file://<WORKSPACE>/src/main/scala/org/scalabridge/sitegen/StaticSiteGenerator.scala
### java.lang.IndexOutOfBoundsException: -1

occurred in the presentation compiler.

presentation compiler configuration:


action parameters:
offset: 700
uri: file://<WORKSPACE>/src/main/scala/org/scalabridge/sitegen/StaticSiteGenerator.scala
text:
```scala
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

  private val h1parser: Parsley[AST] = for {
    in <- (char('#') ~> ws ~> many(satisfy(_ != '\n')) <~ newline)
    value <- NonEmptyString.from(in.mkString) match {
              case Right(v) => Parsley.pure(v)
              case _ => Parsley.empty
            }
  } yield mkNode(value, "h1")

    //...

  private val h2Parser: Parsley[AST] = for@@

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

```



#### Error stacktrace:

```
scala.collection.LinearSeqOps.apply(LinearSeq.scala:129)
	scala.collection.LinearSeqOps.apply$(LinearSeq.scala:128)
	scala.collection.immutable.List.apply(List.scala:79)
	dotty.tools.dotc.util.Signatures$.applyCallInfo(Signatures.scala:244)
	dotty.tools.dotc.util.Signatures$.computeSignatureHelp(Signatures.scala:101)
	dotty.tools.dotc.util.Signatures$.signatureHelp(Signatures.scala:88)
	dotty.tools.pc.SignatureHelpProvider$.signatureHelp(SignatureHelpProvider.scala:46)
	dotty.tools.pc.ScalaPresentationCompiler.signatureHelp$$anonfun$1(ScalaPresentationCompiler.scala:435)
```
#### Short summary: 

java.lang.IndexOutOfBoundsException: -1