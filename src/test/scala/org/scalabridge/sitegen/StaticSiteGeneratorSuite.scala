package org.scalabridge.sitegen

import munit.ScalaCheckSuite
import org.scalabridge.sitegen.StaticSiteGenerator.{generateHtml, h1Parser, linkParser, paragraphParser, parse, parseMany, underLinedParser}
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

  test("Integration test".ignore) {
    assertEquals(
      parseMany(
        """# A title
          |Some __underlined text__. Refer [an example link](http://www.example.com).
          |More text
          |
          |# Another title
          |Another text""".stripMargin
      ).map(trees => generateHtml(trees).map(_.render)),
      Right(
        List(
          "<h1>A title</h1>",
          """<p>Some <u>underlined text</u>. Refer <a href="http://www.example.com">an example link</a>.</p>""",
          "<p>More text.</p>",
          "<h1>Another title</h1>",
          "<p>Another text.</p>"
        )
      )
    )
  }

  test("Integration test 2") {
    assertEquals(
      parseMany(
        """# A title
          |Some
          |
          |More text.
          |
          |# Another title
          |""".stripMargin
      ).map(trees => generateHtml(trees).map(_.render)),
      Right(
        List(
          "<h1>A title</h1>",
          "<p>Some</p>",
          "<p>More text.</p>",
          "<h1>Another title</h1>"
        )
      )
    )
  }

  test("Paragraph") {
    forAll(nonEmptyStringGen) { nes =>
      assertEquals(
        parse(s"$nes\n\n", paragraphParser).map(generateHtml).map(_.render),
        Right(s"<p>$nes</p>")
      )
    }
  }
}