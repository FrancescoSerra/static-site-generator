package org.scalabridge.sitegen

import munit.CatsEffectSuite
import org.scalabridge.sitegen.StaticSiteGenerator.{generateHtml, h1Parser, parse, underLinedParser}

class StaticSiteGeneratorSuite extends CatsEffectSuite {

  test("H1 test") {
    assertEquals(
      parse("# A title\n", h1Parser).map(generateHtml).map(_.render),
      Right("<h1>A title</h1>")
    )
  }

  test("Underlined test1") {
    assertEquals(
      parse("__title__\n", underLinedParser).map(generateHtml).map(_.render),
      Right("<u>title</u>")
    )
  }

  test("Underlined test2") {
    assertEquals(
      parse("__a title__\n", underLinedParser).map(generateHtml).map(_.render),
      Right("<u>a title</u>")
    )
  }
}
