package org.scalabridge.sitegen.generators

import org.scalacheck.Gen
import org.scalabridge.sitegen.domain.model.NonEmptyString
import io.github.iltotore.iron.*

object misc {
  val nonEmptyStringGen: Gen[String :| NonEmptyString] =
    Gen.nonEmptyStringOf(Gen.alphaNumChar).map(_.refineUnsafe)
}
