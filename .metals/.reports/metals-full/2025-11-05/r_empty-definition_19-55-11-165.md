error id: file://<WORKSPACE>/src/test/scala/org/scalabridge/sitegen/StaticSiteGeneratorSuite.scala:scala/package.Right.
file://<WORKSPACE>/src/test/scala/org/scalabridge/sitegen/StaticSiteGeneratorSuite.scala
empty definition using pc, found symbol in pc: scala/package.Right.
empty definition using semanticdb
empty definition using fallback
non-local guesses:
	 -Right.
	 -Right#
	 -Right().
	 -scala/Predef.Right.
	 -scala/Predef.Right#
	 -scala/Predef.Right().
offset: 300
uri: file://<WORKSPACE>/src/test/scala/org/scalabridge/sitegen/StaticSiteGeneratorSuite.scala
text:
```scala
package org.scalabridge.sitegen

import munit.CatsEffectSuite
import org.scalabridge.sitegen.StaticSiteGenerator.{generateHtml, parse}

class StaticSiteGeneratorSuite extends CatsEffectSuite {

  test("H1 test") {
    assertEquals(
      parse("# A title\n").map(generateHtml).map(_.render),
      Ri@@ght("<h1>A title</h1>")
    )
  }

  test("H2 test"){
    assertsEquals(
      parse("## A subtitle\n").map(generateHtml).map(_.render),
      Right("<h2>A subtitle</h2>")
    )
  }
  
}

```


#### Short summary: 

empty definition using pc, found symbol in pc: scala/package.Right.