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

  test("Link test") {
    assertEquals(
      parse("[An example link](http://www.example.com)\n").map(generateHtml).map(_.render),
      Right("""<a href="http://www.example.com">An example link</a>""")
    )
  }
}
