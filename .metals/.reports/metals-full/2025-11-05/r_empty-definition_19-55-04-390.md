error id: file://<WORKSPACE>/src/test/scala/org/scalabridge/sitegen/StaticSiteGeneratorSuite.scala:`<none>`.
file://<WORKSPACE>/src/test/scala/org/scalabridge/sitegen/StaticSiteGeneratorSuite.scala
empty definition using pc, found symbol in pc: `<none>`.
empty definition using semanticdb
empty definition using fallback
non-local guesses:
	 -org/scalabridge/sitegen/StaticSiteGenerator.generateHtml.
	 -org/scalabridge/sitegen/StaticSiteGenerator.generateHtml#
	 -org/scalabridge/sitegen/StaticSiteGenerator.generateHtml().
	 -generateHtml.
	 -generateHtml#
	 -generateHtml().
	 -scala/Predef.generateHtml.
	 -scala/Predef.generateHtml#
	 -scala/Predef.generateHtml().
offset: 270
uri: file://<WORKSPACE>/src/test/scala/org/scalabridge/sitegen/StaticSiteGeneratorSuite.scala
text:
```scala
package org.scalabridge.sitegen

import munit.CatsEffectSuite
import org.scalabridge.sitegen.StaticSiteGenerator.{generateHtml, parse}

class StaticSiteGeneratorSuite extends CatsEffectSuite {

  test("H1 test") {
    assertEquals(
      parse("# A title\n").map(generat@@eHtml).map(_.render),
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

```


#### Short summary: 

empty definition using pc, found symbol in pc: `<none>`.