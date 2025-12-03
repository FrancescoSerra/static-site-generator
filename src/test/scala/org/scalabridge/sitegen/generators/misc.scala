package org.scalabridge.sitegen.generators

import eu.timepit.refined.types.string.NonEmptyString
import org.scalacheck.Gen

object misc {
  val nonEmptyStringGen: Gen[NonEmptyString] =
    Gen.nonEmptyStringOf(Gen.alphaNumChar).map(NonEmptyString.unsafeFrom)

  // poor man's URL generator
  val urlStringGen: Gen[NonEmptyString] =
    Gen.nonEmptyStringOf(Gen.alphaNumChar).map(s => NonEmptyString.unsafeFrom(s"https://$s.com"))
}
