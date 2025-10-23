package org.scalabridge.sitegen

import munit.CatsEffectSuite
import org.scalabridge.sitegen.StaticSiteGenerator.{generateHtml, parse}

class StaticSiteGeneratorSuite extends CatsEffectSuite {

  test("H1 test") {
    assertEquals(
      parse("# A title\n").map(generateHtml).map(_.render),
      Right("<h1>A title</h1>")
    )
  }
}
