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

  test("H2 test"){
    assertsEquals(
      parse("## A subtitle\n").map(generateHtml).map(_.render),
      Right("<h2>A subtitle</h2>")
    )
  }
  

}
