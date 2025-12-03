package org.scalabridge.sitegen

import munit.ScalaCheckSuite
import org.scalabridge.sitegen.StaticSiteGenerator.{
  generateHtml,
  h1Parser,
  linkParser,
  parse,
  underLinedParser
}
import org.scalabridge.sitegen.generators.misc.nonEmptyStringGen
import org.scalacheck.Prop.forAll

class StaticSiteGeneratorSuite extends ScalaCheckSuite {

  test("H1 test") {
    forAll(nonEmptyStringGen) { nes =>
      assertEquals(
        parse(s"#$nes\n", h1Parser).map(generateHtml).map(_.render),
        Right(s"<h1>$nes</h1>")
      )
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

  test("Link") {
    forAll(nonEmptyStringGen, nonEmptyStringGen) { (textNes, urlNes) =>
      assertEquals(
        parse(s"[$textNes]($urlNes)", linkParser).map(generateHtml).map(_.render),
        Right(s"""<a href="$urlNes">$textNes</a>""")
      )
    }
  }
}
