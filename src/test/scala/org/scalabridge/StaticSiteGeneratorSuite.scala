package org.scalabridge

import munit.CatsEffectSuite
import org.scalabridge.StaticSiteGenerator.{parse, generateHtml}

class StaticSiteGeneratorSuite extends CatsEffectSuite {

  test("H1 test") {
    assertEquals(
      parse(Markdown("# A title\n")).map(generateHtml),
      Right(Html("<h1> A title</h1>"))
    )
  }
}
