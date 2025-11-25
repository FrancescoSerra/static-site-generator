package org.scalabridge.sitegen

import munit.ScalaCheckSuite
import org.scalabridge.sitegen.StaticSiteGenerator.*
import org.scalabridge.sitegen.domain.model.AST
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

  test("Bold test") {
    forAll(nonEmptyStringGen) { nes =>
      assertEquals(
        parse(s"**$nes**", strongParser).map(generateHtml).map(_.render),
        Right(s"<strong>$nes</strong>")
      )
    }
  }
  test("Bold failure test") {
    assertEquals(
      parse("*nes**", strongParser).map(generateHtml).map(_.render),
      Left(org.scalabridge.Error("""(line 1, column 1):
                                |  unexpected "*n"
                                |  expected "**"
                                |  >*nes**
                                |   ^^""".stripMargin))
    )
  }
}
