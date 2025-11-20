package org.scalabridge.sitegen

import munit.CatsEffectSuite
import org.scalabridge.sitegen.StaticSiteGenerator.{generateHtml, parse, parseMany}

class StaticSiteGeneratorSuite extends CatsEffectSuite {

  test("H1 test") {
    assertEquals(
      parse("# A title\n").map(generateHtml).map(_.render),
      Right("<h1>A title</h1>")
    )
  }

  test("Link test") {
    assertEquals(
      parse("[An example link](http://www.example.com)").map(generateHtml).map(_.render),
      Right("""<a href="http://www.example.com">An example link</a>""")
    )
  }

  test("Integration test".ignore) {
    assertEquals(
      parseMany(
        """# A title
          |Refer [an example link](http://www.example.com).
          |
          |More text.
          |
          |# Another title
          |Another text""".stripMargin
      ).map(trees => generateHtml(trees).map(_.render)),
      Right(List(
        "<h1>A title</h1>",
        """<p>Refer <a href="http://www.example.com">an example link</a>.</p>""",
        "<p>More text.</p>",
        "<h1>Another title</h1>",
        "<p>Another text.</p>",
      ))
    )
  }
}
