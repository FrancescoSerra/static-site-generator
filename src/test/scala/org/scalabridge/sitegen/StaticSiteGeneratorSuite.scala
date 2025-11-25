package org.scalabridge.sitegen

import munit.ScalaCheckSuite
import org.scalabridge.sitegen.StaticSiteGenerator.{
  generateHtml,
  h1Parser,
  italicParser,
  parse,
  underLinedParser
}
import org.scalabridge.sitegen.generators.misc.{emphasisGen, nonEmptyStringGen}
import org.scalacheck.Prop._

class StaticSiteGeneratorSuite extends ScalaCheckSuite {

  test("H1 test") {
    forAll(nonEmptyStringGen) { nes =>
      assertEquals(
        parse(s"#$nes\n", h1Parser).map(generateHtml).map(_.render),
        Right(s"<h1>$nes</h1>")
      )
    }
  }

  test("italic test") {
    property("matching emphasis marker") = forAll(emphasisGen, nonEmptyStringGen) { (marker, nes) =>
      assertEquals(
        parse(s"${marker}${nes}${marker}", italicParser).map(generateHtml).map(_.render),
        Right(s"<em>$nes</em>")
      )
    }

    property("does not span multiple lines") = forAll(emphasisGen, nonEmptyStringGen) {
      (marker, nes) =>
        assert(
          parse(s"${marker}${nes}\n${marker}", italicParser).isLeft
        )
    }

    property("markers must match") = forAll(emphasisGen, emphasisGen, nonEmptyStringGen) {
      (a, b, nes) =>
        (a != b) ==> assert(parse(s"$a${nes}$b", italicParser).isLeft)
    }
  }

  test("Underlined test") {
    forAll(nonEmptyStringGen) { nes =>
      assertEquals(
        parse(s"__${nes}__\n", underLinedParser).map(generateHtml).map(_.render),
        Right(s"<u>$nes</u>")
      )
    }
  }
}
