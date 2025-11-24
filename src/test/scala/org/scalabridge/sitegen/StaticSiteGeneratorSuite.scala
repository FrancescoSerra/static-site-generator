package org.scalabridge.sitegen

import munit.ScalaCheckSuite
import org.scalabridge.sitegen.StaticSiteGenerator.*
import org.scalabridge.sitegen.domain.model.AST
import org.scalabridge.sitegen.generators.misc.nonEmptyStringGen
import org.scalacheck.Prop.forAll
import parsley.Parsley

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

  test("Bold test") {
    assertEquals(
      parse("**bold text**", strongParser).map(generateHtml).map(_.render),
      Right("<strong>bold text</strong>")
    )
  }
}
